package com.leadtek.nuu.rawuploadService;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leadtek.nuu.componet.MainSend;
import com.leadtek.nuu.entity.Logs;
import com.leadtek.nuu.entity.Users;
import com.leadtek.nuu.rawuploadEntity.MainColumn;
import com.leadtek.nuu.rawuploadEntity.MainTable;
import com.leadtek.nuu.rawuploadEntity.NuuColumnValue;
import com.leadtek.nuu.rawuploadRepository.MainColumnRepository;
import com.leadtek.nuu.rawuploadRepository.MainTableRepository;
import com.leadtek.nuu.rawuploadRepository.NuuColumnValueRepository;
import com.leadtek.nuu.repository.LogsRepository;
import com.leadtek.nuu.repository.UsersRepository;
import com.leadtek.nuu.service.utils.EncrypAES;
import com.leadtek.nuu.service.utils.ExcelDateUtils;

@Service
public class ColumnValueService {

	@Autowired
	LogsRepository logsRepository;

	@Autowired
	MainTableRepository maintable;

	@Autowired
	NuuColumnValueRepository mvr;

	@Autowired
	MainColumnRepository mcr;

	@Autowired
	MainColumnService mcs;

	@Autowired
	UsersRepository usersRepository;

	String password = "nuu";

	public void save(NuuColumnValue value, String user) throws GeneralSecurityException {
		String tableuuid = value.getTableuuid();
		String titlename = "";
//		List<NuuColumnValue> item = mvr.findByTableuuid(tableuuid);
		NuuColumnValue item = new NuuColumnValue();
		if (value.getValueuuid() != null) {
			item = mvr.findByValueuuid(value.getValueuuid());
		}

		List<MainColumn> collist = mcr.findByTableuuid(tableuuid);

		Map<String, String> jsontemp = new LinkedHashMap<>();
		HashMap<String, String> jsobmap = new HashMap<>();
		ObjectMapper mapper = new ObjectMapper();
		try {
			jsobmap = mapper.readValue(value.getColumnvalue(), new TypeReference<HashMap<String, String>>() {
			});
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String pkname = "";
		String pkcode = "";
		for (java.util.Map.Entry<String, String> vo : jsobmap.entrySet()) {
			for (MainColumn transcoluuid : collist) {

				if (vo.getKey().equals(transcoluuid.getColumnename())) {

					if (transcoluuid.getEncode() != null) {
						if (("*").equals(transcoluuid.getEncode())) {
							titlename = transcoluuid.getColumnename();
							byte[] encode = EncrypAES.encrypt(vo.getValue(), password);
							String code = EncrypAES.parseByte2HexStr(encode);
							jsontemp.put(titlename, code);
						} else {
							titlename = transcoluuid.getColumnename();
							jsontemp.put(titlename, vo.getValue());
						}
					} else {
						titlename = transcoluuid.getColumnename();
						jsontemp.put(titlename, vo.getValue());
					}

					if (("pk").equals(transcoluuid.getPk())) {
						pkname = transcoluuid.getColumnename();

						if ("".equals(pkcode)) {
							String tablevalue = vo.getValue();
							pkcode = tablevalue;
						} else {
							String tablevalue = vo.getValue();
							pkcode += "_" + tablevalue;
						}
					}
				}
			}
		}

		NuuColumnValue setv = new NuuColumnValue();
		MainTable itemm = maintable.findByTableuuid(tableuuid);
		if (item != null) {
			if (item.getColumnpkvalue() != null) {
				setv = mvr.findByColumnpkvalue(item.getColumnpkvalue());

				JSONObject result = new JSONObject(jsontemp);
				setv.setColumnpkvalue(pkcode);
				setv.setColumnvalue(result.toString());
				mvr.save(setv);

			}
		} else {// 新增
			JSONObject result = new JSONObject(jsontemp);
			String tablename = itemm.getTablename();
			setv.setTablename(tablename);
			setv.setTableuuid(tableuuid);
			setv.setValueuuid(value.getValueuuid());
			setv.setColumnpkvalue(pkcode);
			setv.setColumnvalue(result.toString());
			mvr.save(setv);
			Date now = new Date();
			itemm.setTotalrow(itemm.getTotalrow() + 1);
			itemm.setLastchange(now);
			maintable.save(itemm);

		}

		if (("1").equals(itemm.getIschange())) {
			List<Users> useraccount = usersRepository.findByrole("倉儲資料管理者");
			for (Users us : useraccount) {

				Date now = new Date();
				String title = "校務倉儲資料系統自動通知" + itemm.getTablecode() + "內容異動";

				String info = itemm.getTablecode() + " 已於" + now + "經" + user + "修改內容。\r\n" + "\r\n"
						+ "此為系統自動寄發內容，請勿直接回覆，如有疑問請聯繫校務研究辦公室*";

				sendMail(us.getAccount(), info, title);

				Logs logs = new Logs();
				logs.setStartTime(new Date());
				logs.setExeTime(new Date());
				logs.setLogtype("RAWDATA");
				logs.setOperation("寄信給:" + us.getAccount() + "," + "內容:" + itemm.getTablename() + "已新增一筆");
				logsRepository.save(logs);
			}
		}

	}

	public void delete(String value, String user) throws GeneralSecurityException {

		NuuColumnValue cl = mvr.findByValueuuid(value);
		String tableuuie = cl.getTableuuid();

		Date now = new Date();
		MainTable item = maintable.findByTableuuid(tableuuie);
		item.setTotalrow(item.getTotalrow() - 1);
		item.setLastchange(now);
		maintable.save(item);
		mvr.deleteByValueuuid(value);

		if (("1").equals(item.getIschange())) {
			List<Users> useraccount = usersRepository.findByrole("倉儲資料管理者");
			for (Users us : useraccount) {

				String title = "校務倉儲資料系統自動通知" + item.getTablecode() + "內容異動";

				String info = item.getTablecode() + " 已於" + now + "經" + user + "修改內容。\r\n" + "\r\n"
						+ "此為系統自動寄發內容，請勿直接回覆，如有疑問請聯繫校務研究辦公室*";

				sendMail(us.getAccount(), info, title);

				Logs logs = new Logs();
				logs.setStartTime(new Date());
				logs.setExeTime(new Date());
				logs.setLogtype("RAWDATA");
				logs.setOperation("寄信給:" + us.getAccount() + "," + "內容:" + item.getTablename() + "已刪除一筆");
				logsRepository.save(logs);

			}
		}
	}

	public Map<String, Object> queryalldata(String value, Integer pageno, Integer pagesize,
			List<Map<String, String>> sort, List<Map<String, String>> filter) throws NumberFormatException {

//		, Integer pageno, Integer pagesize, List<Map<String, String>> sort

		Map<String, Object> finresult = new HashMap<String, Object>();

		List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();

		List<MainColumn> decode = mcs.findbyuuit(value);
		String columnName = "";
		String columnvalue = "";

		// 因儲存方式是JSON SORT用JAVA實作

		Pageable pageable = null;
		String pattern = "^[0-9]{4}-[0-9]{2}-[0-9]{2}$";
		String patterndc = "^[0-9]{4}/[0-9]{2}/[0-9]{2}$";
		pageable = pageable(pageno, pagesize, sort);

		for (Map<String, String> item : filter) {
			columnName = item.get("name");
			columnvalue = item.get("text");
		}

		Page<NuuColumnValue> item = mvr.findByTableuuid(value, pageable);

		String password = "nuu";
		if (item.getSize() > 0 && item != null) {
			for (NuuColumnValue s : item) {
				try {

					HashMap<String, String> temt = new ObjectMapper().readValue(s.getColumnvalue(), HashMap.class);

					for (MainColumn decodeitem : decode) {
						if ("*".equals(decodeitem.getEncode())) {
							String temp = temt.get(decodeitem.getColumnename());
							String finre = "";
							if (temp != null) {
								byte[] decodeitem_s = EncrypAES.parseHexStr2Byte(temp);
								byte[] decryptResult = EncrypAES.decrypt(decodeitem_s, password);
								finre = new String(decryptResult, "UTF-8");
							}
							temt.put(decodeitem.getColumnename(), finre);
						} else {

							if ("DATE".equals(decodeitem.getDatatype())) {
								String temp = temt.get(decodeitem.getColumnename());
								if (temp != null) {
									temp = temp.replace("　", "");

									if (!("").equals(temp)) {
										if (temp.length() == 6) { // 791117
											temt.put(decodeitem.getColumnename(), temp);
										} else if (temp.length() == 7) { // 1081117
											temt.put(decodeitem.getColumnename(), temp);
										} else if (temp.length() == 8) {
											temt.put(decodeitem.getColumnename(), temp);
										} else if (temp.matches(pattern)) {
											temt.put(decodeitem.getColumnename(), temp);
										} else if (temp.matches(patterndc)) {
											temt.put(decodeitem.getColumnename(), temp);
										} else {
											try {
												String sp[] = temp.split("\\.");
												int i = Integer.valueOf(sp[0]);
												String finday = formatExcelDate(i);
												temt.put(decodeitem.getColumnename(), finday);
											} catch (NumberFormatException e) {
												temt.put(decodeitem.getColumnename(), temp);
											}

										}
									}

								}

							}

							String temp = temt.get(decodeitem.getColumnename());
							// 模糊查詢匹配
//							Pattern valuepattern = Pattern.compile(columnvalue);
//							Matcher matcher = valuepattern.matcher(temt.get(columnName));
//							if(matcher.matches()) {
							temt.put(decodeitem.getColumnename(), temp);
//							}

						}
					}
					temt.put("valueuuid", s.getValueuuid());
					temt.put("columnpkvalue", s.getColumnpkvalue());

					if (!"".equals(columnvalue) && !("").equals(columnName)) {
						Pattern valuepattern = Pattern.compile(columnvalue);
						Matcher matcher = valuepattern.matcher(temt.get(columnName));
						if (matcher.find()) {
							result.add(temt);
						}
					} else {
						result.add(temt);
					}

				} catch (JsonParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JsonMappingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}

			}
		}

		String ordervalue = "";
		String namevalue = "";
		if (sort != null) {
			if (sort.size() > 0) {
				for (Map<String, String> obj : sort) {
					ordervalue = obj.get("order");
					namevalue = obj.get("name");
				}
			}
		}

		if ("asc".equals(ordervalue)) {
			Collections.sort(result, new MapComparatorAsc(namevalue));
		}
		if ("desc".equals(ordervalue)) {
			Collections.sort(result, new MapComparatorDesc(namevalue));
		}

		finresult.put("columnvalue", result);
		finresult.put("pagevalue", item);

		return finresult;
	}

	private String formatExcelDate(int day) {
		Calendar calendar = new GregorianCalendar(1900, 0, -1);
		Date gregorianDate = calendar.getTime();
		String formatExcelDate = ExcelDateUtils.format(ExcelDateUtils.addDay(gregorianDate, day),
				ExcelDateUtils.YYYYMMDD);
		return formatExcelDate;
	}

	public Pageable pageable(Integer pageno, Integer pagesize, List<Map<String, String>> item) {
		Pageable pageable = null;

		String ordervalue = "";
		String namevalue = "";

		if (item.size() > 0) {
			for (Map<String, String> obj : item) {
				ordervalue = obj.get("order");
				namevalue = obj.get("name");
			}

			if (item != null) {
				if ("asc".equals(ordervalue)) {
					Sort sortitem = new Sort(Direction.ASC, namevalue);
					pageable = PageRequest.of(pageno, pagesize, sortitem);
				} else if ("desc".equals(ordervalue)) {
					Sort sortitem = new Sort(Direction.DESC, namevalue);
					pageable = PageRequest.of(pageno, pagesize, sortitem);
				}
			} else {
				pageable = PageRequest.of(pageno, pagesize);
			}
		} else {
			pageable = PageRequest.of(pageno, pagesize);
		}

		return pageable;
	}

	void sendMail(String auth, String mailinfo, String title) throws GeneralSecurityException {

		MainSend sendpa = new MainSend();
		sendpa.send(auth, mailinfo, title);

	}

	public Specification<NuuColumnValue> buildFilters(List<Map<String, String>> filters, String tableuuid) {

		Specification<NuuColumnValue> specification = new Specification<NuuColumnValue>() {
			@Override
			public Predicate toPredicate(Root<NuuColumnValue> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicate = new ArrayList<>();

				String columnName = "";
				String value = "";
				if (filters.size() > 0) {
					for (Map<String, String> item : filters) {
						columnName = item.get("name");
						value = item.get("text");
					}

					if (!StringUtils.isEmpty(columnName) && !("").equals(columnName) && !StringUtils.isEmpty(value)
							&& !("").equals(value)) {
						Predicate authName = criteriaBuilder.like(root.get(columnName), '%' + value + '%');
						predicate.add(authName);

					}

				}

				Predicate[] predicates = new Predicate[predicate.size()];

				return criteriaBuilder.and(predicate.toArray(predicates));
			}
		};
		return specification;

	}

	public class MapComparatorDesc implements Comparator<Map<String, String>> {

		String namevalue = "";

		public MapComparatorDesc(String namevalue) {
			this.namevalue = namevalue;
		}

		@Override
		public int compare(Map<String, String> m1, Map<String, String> m2) {

			return (m2.get(namevalue).compareTo(m1.get(namevalue)));
		}

	}

	static class MapComparatorAsc implements Comparator<Map<String, String>> {

		String namevalue = "";

		public MapComparatorAsc(String namevalue) {
			this.namevalue = namevalue;
		}

		@Override
		public int compare(Map<String, String> m1, Map<String, String> m2) {

			return (m1.get(namevalue).compareTo(m2.get(namevalue)));

		}

	}

}
