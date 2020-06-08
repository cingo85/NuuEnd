package com.leadtek.nuu.etlService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.leadtek.nuu.componet.MainSend;
import com.leadtek.nuu.componet.WebSocketComponent;

import com.leadtek.nuu.entity.Logs;
import com.leadtek.nuu.entity.Users;
import com.leadtek.nuu.etlEntity.ColumnList;
import com.leadtek.nuu.etlEntity.ETlMaster;
import com.leadtek.nuu.etlEntity.EtlAuth;
import com.leadtek.nuu.etlEntity.EtlDetail;
import com.leadtek.nuu.etlRepository.EtlAuthRepository;
import com.leadtek.nuu.etlRepository.EtlDetailRepository;
import com.leadtek.nuu.etlRepository.EtlMasterRepository;
import com.leadtek.nuu.repository.LogsRepository;
import com.leadtek.nuu.repository.UsersRepository;
import com.leadtek.nuu.service.utils.EncrypAES;

@Service
public class EtlService {

	@Autowired
	LogsRepository logsRepository;

	@Autowired
	EtlMasterRepository etlMasterRepository;

	@Autowired
	EtlDetailRepository etlDetailRepository;

	@Autowired
	EtlAuthRepository etlauthRepository;

	@Autowired
	WebSocketComponent WebSocketComponent;

	@Autowired
	UsersRepository usersRepository;

	String password = "nuu";

	public ETlMaster findbyid(Integer id) {
		return etlMasterRepository.findByid(id);
	}

	public EtlDetail findetldetailbyid(Integer id) {
		return etlDetailRepository.findByid(id);
	}

	public List<ETlMaster> findAllTable() {
		// TODO Auto-generated method stub
		return etlMasterRepository.findAll();
	}

	public List<ETlMaster> updateSchedule(ETlMaster item) {
		etlMasterRepository.save(item);
		return etlMasterRepository.findAll();
	}

	public List<EtlAuth> findalletlauth(String authname, String tablename) {

		List<EtlAuth> result = new ArrayList<>();
		List<EtlAuth> item = etlauthRepository.findBytablename(tablename);
		for (EtlAuth temp : item) {
			if (temp.getAuthName().equals(authname)) {
				result.add(temp);
			}
		}
		return result;
	}

	public List<EtlAuth> saveetlauth(EtlAuth etlAuth) {
		etlauthRepository.save(etlAuth);

		List<EtlAuth> result = new ArrayList<>();
		List<EtlAuth> item = etlauthRepository.findBytablename(etlAuth.getTablename());
		for (EtlAuth temp : item) {
			if (temp.getAuthName().equals(etlAuth.getAuthName())) {
				result.add(temp);
			}
		}
		return result;
	}

	public Map<String, List<Map<String, Object>>> findTableColumn(String tablename, String role) {

		List<EtlDetail> item = etlDetailRepository.findBytablename(tablename);

		Map<String, List<Map<String, Object>>> restmap = new HashMap<>();

		for (EtlDetail temp : item) {
			boolean authcoulumn = false;

			String[] authtemp = temp.getAuthName();
			for (String s : authtemp) {
				String roleauth[] = s.split(":");
				if (role.equals(roleauth[0]) && !("2").equals(roleauth[1])) {
					authcoulumn = true;
					break;
				}

			}
			if (authcoulumn) {
				if (restmap.get(temp.getColumngroup()) == null) {
					restmap.put(temp.getColumngroup(), new ArrayList());
					Map<String, Object> es = new LinkedHashMap<>();
					es.put("id", temp.getId());
					es.put("columnengname", temp.getColumnengname());
					es.put("columnname", temp.getColumnname());
					es.put("authName", temp.getAuthName());
					restmap.get(temp.getColumngroup()).add(es);
				} else {
					Map<String, Object> es = new LinkedHashMap<>();
					es.put("id", temp.getId());
					es.put("columnengname", temp.getColumnengname());
					es.put("columnname", temp.getColumnname());
					es.put("authName", temp.getAuthName());
					restmap.get(temp.getColumngroup()).add(es);
				}
			}

		}

		// 元培版
//		for (EtlDetail temp : item) {
//			EtlDetail item2 = new EtlDetail();
//			item2.setTablename(tablename);
//			item2.setColumnengname(temp.getColumnengname());
//			item2.setColumnname(temp.getColumnname());
//			fin.add(item2);
//		}

		// TODO Auto-generated method stub
		return restmap;
	}

	@Async
	public List<ETlMaster> dataclean(String tablename) throws Exception {

		String result = "";

		int countStatus = 0;

		List<ETlMaster> allitem = etlMasterRepository.findAll();

		for (ETlMaster item : allitem) {
			countStatus += item.getStatus();
		}
		try {
			if (countStatus == 0) {
				WebSocketComponent.sendMessage("Start");
				ETlMaster item = etlMasterRepository.findBytablename(tablename);

				item.setStatus(1);
				etlMasterRepository.save(item);
				PostFunction temr = new PostFunction();
				result = temr.sendPost(item.getTableengname());
				System.out.println("回傳筆數" + result);
				WebSocketComponent.sendMessage("Complete");
				saveUpdateStatus(tablename, result);

				List<ETlMaster> finallyItem = etlMasterRepository.findAll();
				return finallyItem;
			} else {
				WebSocketComponent.sendMessage("Start");
				return allitem;
			}
		} catch (Exception e) {
			ETlMaster item = etlMasterRepository.findBytablename(tablename);
			saveUpdateStatus(tablename, item.getTablecount().toString());
		}
		return allitem;
	}

	@Async
	public void saveUpdateStatus(String tablename, String result) throws GeneralSecurityException {
		ETlMaster finitem = etlMasterRepository.findBytablename(tablename);
		Date now = new Date();
		int i = Integer.valueOf(result);
		finitem.setTablecount(i);
		finitem.setCleandatecal(now);
		finitem.setStatus(0);

		if (finitem.getEndsend().equals("1")) {
			// 寄信
			String auth[] = finitem.getAuthName();

			String title = "校務倉儲資料系統自動通知" + tablename + "資料清洗已完成";

			String info = tablename + " 資料清洗已完成。\r\n" + "\r\n" + "此為系統自動寄發內容，請勿直接回覆，如有疑問請聯繫校務研究辦公室*";

			sendMail(auth, info, title);
			Logs logs = new Logs();
			logs.setStartTime(new Date());
			logs.setExeTime(new Date());
			logs.setLogtype("ETL");
			logs.setOperation("寄信給:" + auth + "," + "內容:" + info);
			logsRepository.save(logs);
		}

		etlMasterRepository.save(finitem);
	}

	public ByteArrayInputStream contactListToExcelFileByJoin(List<ColumnList> ColumnList, String role)
			throws SQLException, IOException {

		Workbook workbook = new SXSSFWorkbook(1000000); // 建立檔案
		Sheet sheet = workbook.createSheet("表單");// 建立工作表

		Map<String, List<Map<String, String>>> outputInfo = new LinkedHashMap<>();

		Map<String, String> colengandcht = new LinkedHashMap<>();
		Set<String> columninfolist = new HashSet<>();

		for (ColumnList clitem : ColumnList) {

			String columngroup = clitem.getTablename();
			List<String> columns = clitem.getColumns();

			List<EtlDetail> etldbinfo = etlDetailRepository.findBycolumngroup(columngroup);

			for (EtlDetail item : etldbinfo) {

				for (String cols : columns) {

					if (cols.equals(item.getColumnengname())) {
						Map<String, String> temp = new LinkedHashMap<String, String>();
						colengandcht.put(item.getColumnengname(), item.getColumnname());
						columninfolist.add(item.getColumnname());
						if (outputInfo.get(item.getEtltable()) == null) {
							outputInfo.put(item.getEtltable(), new ArrayList<>());

							String[] authname = item.getAuthName();
							for (String roles : authname) {
								String sp[] = roles.split(":");
								if (sp[0].equals(role)) {
									temp.put("auth", sp[1]);
								}
							}
							temp.put("columnType", item.getColumngroup());
							temp.put("columnCname", item.getColumnname());
							temp.put("columnName", cols);
							temp.put("columnencode", item.getColumnencryption());
							outputInfo.get(item.getEtltable()).add(temp);
						} else {
							String[] authname = item.getAuthName();
							for (String roles : authname) {
								String sp[] = roles.split(":");
								if (sp[0].equals(role)) {
									temp.put("auth", sp[1]);
								}
							}
							temp.put("columnType", item.getColumngroup());
							temp.put("columnCname", item.getColumnname());
							temp.put("columnName", cols);
							temp.put("columnencode", item.getColumnencryption());
							outputInfo.get(item.getEtltable()).add(temp);
						}

					}

				}

			}

		}

		List<String> coulmus = new ArrayList<>();

		List<String> defaultcolumnname = new ArrayList<>();

		String lj[] = new String[20];

		int i = 0;
		String defaultqlString = "";
		String dbsqlString = "";
		String mainquerytable = "";
		String mainkey = "";
		List<String> mainkeyList = new ArrayList<>();
		String mainta = "";
		ArrayList<String> leftjoin = new ArrayList<>();
		List<LinkedHashMap<String, String>> rstemp = new ArrayList<>();
		List<String> columnname = new ArrayList<>();
		for (Entry<String, List<Map<String, String>>> item : outputInfo.entrySet()) {
			if (i != 0) {
				defaultqlString.replace(lj[i - 1] + ".", lj[i] + ".");
			}
			List<Map<String, String>> coulumschema = item.getValue();
			String qlString = "";
			String columnType = "";
			for (Map<String, String> info : coulumschema) {
				String mainkeytemp = "";
				if ("固定帶出".equals(info.get("columnType"))) {
					columnType = info.get("columnType");
					defaultcolumnname.add(info.get("columnCname"));

					lj[i] = item.getKey();
					if ("".equals(defaultqlString)) {
						defaultqlString += "SELECT " + lj[i] + "." + info.get("columnName") + " AS " + "'"
								+ info.get("columnCname") + "'";
					} else {
						defaultqlString += " ," + lj[i] + "." + info.get("columnName") + " AS " + "'"
								+ info.get("columnCname") + "'";
					}
					mainkeytemp = lj[i] + "." + info.get("columnName");
					mainkeyList.add(mainkeytemp);
				}
			}

			for (Map<String, String> info : coulumschema) {
				if (!"固定帶出".equals(info.get("columnType"))) {
					lj[i] = item.getKey();
					if (i == 0) {
						columnType = info.get("columnType");
						columnname.add(info.get("columnCname"));
						if ("".equals(qlString)) {
							qlString += defaultqlString + "," + lj[i] + "." + info.get("columnName") + " AS " + "'"
									+ info.get("columnCname") + "'";
						} else {
							qlString += " ," + lj[i] + "." + info.get("columnName") + " AS " + "'"
									+ info.get("columnCname") + "'";
						}
						mainta = lj[i];
					}

					if (i != 0) {
						columnType = info.get("columnType");
						columnname.add(info.get("columnCname"));
						defaultqlString = defaultqlString.replace(lj[i - 1], lj[i]);

						dbsqlString += "," + lj[i] + "." + info.get("columnName") + " AS " + "'"
								+ info.get("columnCname") + "'" + qlString;

					}

				}

			}
			if (i != 0) {

				for (String s : mainkeyList) {
					if (leftjoin.size() == 0) {
						String sdd = " left join " + item.getKey() + " on " + s + "="
								+ s.replace(lj[i - 1] + ".", lj[i] + ".");
						leftjoin.add(sdd);
					} else {
						String sdd = " AND " + s + "=" + s.replace(lj[i - 1] + ".", lj[i] + ".");
						leftjoin.add(sdd);
					}
				}
			}

			qlString += dbsqlString;
			if (!"固定帶出".equals(columnType)) {

				dbsqlString = qlString;
				if (i == 0) {
					mainquerytable = " FROM " + mainta;
				} else {
					mainquerytable = " FROM " + mainta;
					for (String ltitem : leftjoin) {
						mainquerytable += ltitem;
					}
				}
				columnname.addAll(defaultcolumnname);
				i += 1;
			}

		}
		System.out.println(dbsqlString + mainquerytable);
		List<LinkedHashMap<String, String>> rs = conn(dbsqlString + mainquerytable, columnname);
		rstemp.addAll(rs);
		createrow(columninfolist, rstemp, sheet);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		workbook.write(outputStream);
		return new ByteArrayInputStream(outputStream.toByteArray());

	}

	public static String outputFile = "C:/Users/Admin/Desktop/output/output.xlsx";

	public ByteArrayInputStream contactListToExcelFileByView(ColumnList ColumnList, String role)
			throws SQLException, IOException {
		Workbook workbook = new SXSSFWorkbook(1000000); // 建立檔案
		Sheet sheet = workbook.createSheet("表單");// 建立工作表

		Map<String, List<Map<String, String>>> outputInfo = new LinkedHashMap<>();

		Map<String, String> colengandcht = new LinkedHashMap<>();
		Set<String> columninfolist = new HashSet<>();

		String columngroup = ColumnList.getTablename();
		List<String> columns = ColumnList.getColumns();
		List<String> year = ColumnList.getYear();

		String outyear = ColumnList.getAlldatayear();

		List<EtlDetail> etldbinfo = etlDetailRepository.findByetltable(columngroup);
		String tablename = "";
		for (EtlDetail item : etldbinfo) {
			tablename = item.getEtltable();
			for (String cols : columns) {

				if (cols.equals(item.getColumnengname())) {
					Map<String, String> temp = new LinkedHashMap<String, String>();
					colengandcht.put(item.getColumnengname(), item.getColumnname());
					columninfolist.add(item.getColumnname());
					if (outputInfo.get(item.getEtltable()) == null) {
						outputInfo.put(item.getEtltable(), new ArrayList<>());

						String[] authname = item.getAuthName();
						for (String roles : authname) {
							String sp[] = roles.split(":");
							if (sp[0].equals(role)) {
								temp.put("auth", sp[1]);
							}
						}
						temp.put("columnType", item.getColumngroup());
						temp.put("columnCname", item.getColumnname());
						temp.put("columnName", cols);
						temp.put("columnencode", item.getColumnencryption());
						outputInfo.get(item.getEtltable()).add(temp);
					} else {
						String[] authname = item.getAuthName();
						for (String roles : authname) {
							String sp[] = roles.split(":");
							if (sp[0].equals(role)) {
								temp.put("auth", sp[1]);
							}
						}
						temp.put("columnType", item.getColumngroup());
						temp.put("columnCname", item.getColumnname());
						temp.put("columnName", cols);
						temp.put("columnencode", item.getColumnencryption());
						outputInfo.get(item.getEtltable()).add(temp);
					}

				}

			}

		}

		List<String> defaultcolumnname = new ArrayList<>();

		String lj[] = new String[20];

		int i = 0;
		String defaultqlString = "";
		String dbsqlString = "";
		String mainquerytable = "";
		String whereString = "";
		List<String> mainkeyList = new ArrayList<>();
		String mainta = "";
		ArrayList<String> leftjoin = new ArrayList<>();
		List<LinkedHashMap<String, String>> rstemp = new ArrayList<>();
		List<String> columnname = new ArrayList<>();
		for (Entry<String, List<Map<String, String>>> item : outputInfo.entrySet()) {
			if (i != 0) {
				defaultqlString.replace(lj[i - 1] + ".", lj[i] + ".");
			}
			List<Map<String, String>> coulumschema = item.getValue();
			String qlString = "";
			String columnType = "";
			for (Map<String, String> info : coulumschema) {
				String mainkeytemp = "";
				if ("固定帶出".equals(info.get("columnType"))) {
					columnType = info.get("columnType");
					defaultcolumnname
							.add(info.get("columnCname") + ";" + info.get("auth") + ";" + info.get("columnencode"));

					lj[i] = item.getKey();
					if ("".equals(defaultqlString)) {
						defaultqlString += "SELECT " + info.get("columnName") + " AS " + "'" + info.get("columnCname")
								+ "'";
					} else {
						defaultqlString += " ," + info.get("columnName") + " AS " + "'" + info.get("columnCname") + "'";
					}
					mainkeytemp = info.get("columnName");
					mainkeyList.add(mainkeytemp);
				}
			}

			for (Map<String, String> info : coulumschema) {
				if (!"固定帶出".equals(info.get("columnType"))) {
					lj[i] = item.getKey();
					if (i == 0) {
						columnType = info.get("columnType");
						columnname
								.add(info.get("columnCname") + ";" + info.get("auth") + ";" + info.get("columnencode"));
						if ("".equals(qlString)) {
							qlString += defaultqlString + "," + info.get("columnName") + " AS " + "'"
									+ info.get("columnCname") + "'";
						} else {
							qlString += " ," + info.get("columnName") + " AS " + "'" + info.get("columnCname") + "'";
						}
						mainta = lj[i];
					}

					if (i != 0) {
						columnType = info.get("columnType");
						columnname
								.add(info.get("columnCname") + ";" + info.get("auth") + ";" + info.get("columnencode"));

						dbsqlString += "," + info.get("columnName") + " AS " + "'" + info.get("columnCname") + "'"
								+ qlString;

					}

				}

			}

			qlString += dbsqlString;
			if (!"固定帶出".equals(columnType)) {
				if (!"".equals(outyear) || !(outyear == null)) {
					dbsqlString = qlString;
					if (i == 0) {
						mainquerytable = " FROM " + tablename;
					}
					columnname.addAll(defaultcolumnname);
					i += 1;
				}

			}else {
				dbsqlString = defaultqlString;
				mainquerytable = " FROM " + tablename;
			}
			
			
			
			if ("stuscore".equals(tablename)) {
				mainquerytable += "_" + "course" + "_" + outyear;
			}

		}

		if (year != null) {
			if (year.size() != 0) {
				if (!"courseinfo".equals(tablename) && !"stuterm".equals(tablename)
						&& !"stusportlist".equals(tablename)) {
					if (year.size() > 1) {
						String yearsql = "";
						for (String s : year) {
							if ("".equals(yearsql)) {
								yearsql += "'" + s + "'";
							} else {
								yearsql += ",'" + s + "'";
							}
						}
						whereString = " WHERE EnrollSYear in(" + yearsql + ")";

					} else {
						whereString = " WHERE EnrollSYear = '" + year.get(0) + "'";
					}

				} else {
					if (year.size() > 1) {
						String yearsql = "";
						for (String s : year) {
							if ("".equals(yearsql)) {
								yearsql += "'" + s + "'";
							} else {
								yearsql += ",'" + s + "'";
							}
						}
						whereString = " WHERE SYear in(" + yearsql + ")";

					} else {
						whereString = " WHERE SYear = '" + year.get(0) + "'";
					}
				}
			}
		}
		System.out.println(dbsqlString + mainquerytable + whereString);
		List<LinkedHashMap<String, String>> rs = conn(dbsqlString + mainquerytable + whereString, columnname);
		rstemp.addAll(rs);
		createrow(columninfolist, rstemp, sheet);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		workbook.write(outputStream);

		return new ByteArrayInputStream(outputStream.toByteArray());

	}

	public void createrow(Set<String> size, List<LinkedHashMap<String, String>> rstemp, Sheet sheet) {

		int rowNum = 1;
		Map<String, Map<String, String>> temp = new LinkedHashMap<>();

		for (Map<String, String> titlevalue : rstemp) {
			Row row = sheet.createRow(0);
			int i = 0;
			for (Entry<String, String> entry : titlevalue.entrySet()) {
				row.createCell(i).setCellValue(entry.getKey());
				i += 1;
			}
		}

		for (Map<String, String> itemvalue : rstemp) {
			Row row = sheet.createRow(rowNum);
			int j = 0;
			for (Entry<String, String> entry : itemvalue.entrySet()) {
				if (sheet.getRow(0) != null) {
					if (sheet.getRow(0).getCell(j) != null) {
						String title = sheet.getRow(0).getCell(j).getStringCellValue();
						if (entry.getKey().equals(title)) {
							row.createCell(j).setCellValue(entry.getValue());
							j += 1;
						}
					}
				}

			}
			rowNum++;
		}
	}

	void sendMail(String auth[], String mailinfo, String title) throws GeneralSecurityException {
		for (String item : auth) {
			List<Users> usitem = usersRepository.findByrole("倉儲資料管理者");
			for (Users sendmain : usitem) {
				MainSend sendpa = new MainSend();
				sendpa.send(sendmain.getAccount(), mailinfo, title);
			}
		}
	}

	public void saveetldetail(List<Map<String, Object>> savemap) {
		for (Map<String, Object> seitem : savemap) {
			Integer id = (Integer) seitem.get("id");

			Map<String, String> newvaluemap = new HashMap<>();
			Map<String, String> oldvaluemap = new HashMap<>();

			List<String> result = new ArrayList<>();

			List<String> apiauthname = (ArrayList) seitem.get("authName");
			for (String item : apiauthname) {
				String sp[] = item.split(":");
				newvaluemap.put(sp[0], sp[1]);
			}

			EtlDetail raw_item = etlDetailRepository.findByid(id);
			String raw_authName[] = raw_item.getAuthName();
			for (String item : raw_authName) {
				String sp[] = item.split(":");
				oldvaluemap.put(sp[0], sp[1]);
			}

			Iterator<String> rawkey = oldvaluemap.keySet().iterator();
			while (rawkey.hasNext()) {
				String mapkey = rawkey.next();
				String oldvalue = oldvaluemap.get(mapkey);
				String newvalue = newvaluemap.get(mapkey);

				if (!oldvalue.equals(newvalue)) {
					if (newvalue != null) {
						result.add(mapkey + ":" + newvalue);
					} else {
						result.add(mapkey + ":" + oldvalue);
					}
				} else {
					result.add(mapkey + ":" + oldvalue);
				}

			}

			String changeFin[] = result.toArray(new String[0]);

			raw_item.setAuthName(changeFin);

			etlDetailRepository.save(raw_item);
		}

	}

	public Map<String, List<Map<String, String>>> ttt = new HashMap<>();

	public List<LinkedHashMap<String, String>> conn(String sql, List<String> columnname)
			throws SQLException, UnsupportedEncodingException {
		List<LinkedHashMap<String, String>> obj = new ArrayList<LinkedHashMap<String, String>>();

		String url = "jdbc:mysql://203.64.173.61:3306/nuu?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8";
		String user = "Leadtek";
		String pass = "Leadtek21191";
		ResultSet rs = null;
		Connection con = null;
		Statement stmt = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			con = DriverManager.getConnection(url, user, pass);
			long startQueryTime = System.nanoTime();

			stmt = con.createStatement();
			stmt.execute(sql);
			rs = stmt.getResultSet();
			long endQueryTime = System.nanoTime();
			System.out.println("結束查詢時間 : " + (endQueryTime - startQueryTime) / 1000000000 + "seconds");
			long rsnext = System.nanoTime();

			while (rs.next()) {

				LinkedHashMap<String, String> temp = new LinkedHashMap<>();
				for (String s : columnname) {
					String spt[] = s.split(";");
					if (!"2".equals(spt[1])) {
						if ("0".equals(spt[1]) || "1".equals(spt[1])) {
							if (spt.length > 2) {
								if (!"".equals(spt[2]) && "0".equals(spt[1])) {

									String finre = "";
									if (rs.getString(spt[0]) != null) {
										byte[] decodeitem_s = EncrypAES.parseHexStr2Byte(rs.getString(spt[0]));
										byte[] decryptResult = EncrypAES.decrypt(decodeitem_s, password);
										finre = new String(decryptResult, "UTF-8");
										temp.put(spt[0], finre);
									}

								} else {
									temp.put(spt[0], rs.getString(spt[0]));
								}
							} else {
								temp.put(spt[0], rs.getString(spt[0]));
							}

						} else {
							temp.put(spt[0], rs.getString(spt[0]));
						}

					}
				}
				obj.add(temp);

			}
			long rsnextend = System.nanoTime();
			System.out.println("rsnext結束資料處裡時間 : " + (rsnextend - rsnext) / 1000000000 + "seconds");
			rs.last();
		} catch (ClassNotFoundException ex) {

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) {
				rs.close();
			}

			if (con != null) {
				con.close();
			}

			if (stmt != null) {
				stmt.close();
			}

		}
		return obj;
	}

}
