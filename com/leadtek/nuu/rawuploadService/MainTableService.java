package com.leadtek.nuu.rawuploadService;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

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
public class MainTableService {
	@Autowired
	MainTableRepository maintable;

	@Autowired
	MainColumnRepository maincolumn;

	@Autowired
	NuuColumnValueRepository nuucolumnvalue;

	@Autowired
	LogsRepository logsRepository;

//	@Autowired
//	MongoTemplate mongoTemplate;

	@Autowired
	UsersRepository usersRepository;

	private final String password = "nuu";

	public void tablesave(MainTable entity) {
		Date now = new Date();

		MainTable item = maintable.findByTableuuid(entity.getTableuuid());

		if (!(entity.getAuthName().equals(item.getAuthName()))) {
			item.setAuthName(entity.getAuthName());
		}

		item.setStartdate(entity.getStartdate());
		item.setStartsend(entity.getStartsend());
		item.setEnddate(entity.getEnddate());
		item.setEndsend(entity.getEndsend());
		item.setStartupload(entity.getStartupload());
		item.setTotalrow(item.getTotalrow());
		item.setLastchange(now);

		maintable.save(item);
	}

	public void tablebatch(MainTable entity) {
		maintable.save(entity);
	}

	public void columnsave(MainColumn entity) {
		maincolumn.save(entity);
	}

	public List<MainTable> findall() {

		return maintable.findAll();
	}

	public Page<MainTable> findall(Integer pageno, Integer pagesize, List<Map<String, String>> sort, String role,
			List<Map<String, String>> filters) {

		String columnName = "";
		String value = "";

		Specification<MainTable> spec = buildFilters(filters, role);

		for (Map<String, String> item : filters) {
			columnName = item.get("name");
			value = item.get("text");
		}

		Page<MainTable> result = null;
		Pageable pageable = null;
		pageable = pageable(pageno, pagesize, sort);
		Page<MainTable> item = maintable.findAll(spec, pageable);

		return item;
	}

//	public Page<MainTable> findall(Integer pageno, Integer pagesize, List<Map<String, String>> sort,String role,
//			List<Map<String, String>> filters) {
//
//		String columnName = "";
//		String value = "";
//
//		for (Map<String, String> item : filters) {
//			columnName = item.get("name");
//			value = item.get("text");
//		}
//		Specification<MainTable> spec = buildFilters(filters, role);
//		Pageable pageable = null;
//		pageable = pageable(pageno, pagesize, sort);
//		Page<MainTable> item = maintable.findAll(spec,pageable);
//
//		return item;
//	}

	public MainTable findByTableuuid(String tableuuid) {
		MainTable item = maintable.findByTableuuid(tableuuid);
		if (item.getTotalrow() == null) {
			item.setTotalrow(0);
		}
		return item;
	}

	public Map<String, Object> batchImport(String fileName, MultipartFile file) {
//		if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
//			throw new Exception("上傳文件格式不正確");
//		}
		Map<String, Object> result = new LinkedHashMap<>();

		try {

			InputStream is = file.getInputStream();
			XSSFWorkbook workbook = new XSSFWorkbook(is);

			int sheets = workbook.getNumberOfSheets();

			Map<String, ArrayList<MainColumn>> temp = new LinkedHashMap<>();

			for (int i = 0; i < sheets; i++) {
				XSSFSheet sheet = workbook.getSheetAt(i);
				int rows = sheet.getPhysicalNumberOfRows();

				for (int j = 1; j < rows; j++) {

					XSSFRow row = sheet.getRow(j);

					String tablecode = "";
					String tablename = "";

					if (transToString(row, 0) != null) {
						tablecode = transToString(row, 0).getStringCellValue();
					}
					if (transToString(row, 1) != null) {
						tablename = transToString(row, 1).getStringCellValue();
					}
					
					if (temp.get(tablecode + ";" + tablename) == null) {
						temp.put(tablecode + ";" + tablename, new ArrayList<MainColumn>());
						MainColumn item = new MainColumn();

						if (transToString(row, 0) != null) {
							item.setColumntable(transToString(row, 0).getStringCellValue());
						}

						if (transToString(row, 2) != null) {
							item.setColumnename(transToString(row, 2).getStringCellValue());
						}
						if (transToString(row, 3) != null) {
							item.setColumncname(transToString(row, 3).getStringCellValue());
						}
						if (transToString(row, 4) != null) {
							item.setDatatype(transToString(row, 4).getStringCellValue());
						}

						if (transToString(row, 5) != null) {
							item.setNote(transToString(row, 5).getStringCellValue());
						}
						if (transToString(row, 6) != null) {
							item.setPk(transToString(row, 6).getStringCellValue());
						}
						if (transToString(row, 7) != null) {
							item.setSort(transToString(row, 7).getStringCellValue());
						}
						if (transToString(row, 8) != null) {
							item.setEncode(transToString(row, 8).getStringCellValue());
						}
						temp.get(tablecode + ";" + tablename).add(item);
					} else {
						MainColumn item = new MainColumn();

						if (transToString(row, 0) != null) {
							item.setColumntable(transToString(row, 0).getStringCellValue());
						}
						if (transToString(row, 2) != null) {
							item.setColumnename(transToString(row, 2).getStringCellValue());
						}
						if (transToString(row, 3) != null) {
							item.setColumncname(transToString(row, 3).getStringCellValue());
						}
						if (transToString(row, 4) != null) {
							item.setDatatype(transToString(row, 4).getStringCellValue());
						}
						
						if (transToString(row, 7) != null) {
							item.setPk(transToString(row, 7).getStringCellValue());
						}
						if (transToString(row, 8) != null) {
							item.setSort(transToString(row, 8).getStringCellValue());
						}
						if (transToString(row, 9) != null) {
							item.setEncode(transToString(row, 9).getStringCellValue());
						}
						if (transToString(row, 6) != null) {
							item.setNote(transToString(row, 6).getStringCellValue());
						}
						temp.get(tablecode + ";" + tablename).add(item);
					}

				}

			}

			temp.forEach(new BiConsumer<String, ArrayList<MainColumn>>() {

				@Override
				public void accept(String t, ArrayList<MainColumn> u) {

					String temp[] = t.split(";");

					UUID tableuuid = UUID.randomUUID();
					String talbeuuidsave = tableuuid.toString();
					talbeuuidsave = talbeuuidsave.toUpperCase();

					MainTable entity = new MainTable();
					entity.setTablecode(temp[0]);
					entity.setTablename(temp[1]);
					entity.setTotalrow(0);
					entity.setIschange("1");
					entity.setStartsend("1");
					entity.setEndsend("1");
					entity.setTableuuid(talbeuuidsave);
					tablebatch(entity);

					for (MainColumn colitem : u) {
						MainColumn colentity = (MainColumn) colitem;

						UUID coluuid = UUID.randomUUID();
						String coluuidsave = coluuid.toString();
						coluuidsave = coluuidsave.toUpperCase();
						colentity.setColumnuuid(coluuidsave);
						colentity.setTableuuid(talbeuuidsave);
						colentity.setTablename(temp[1]);
						columnsave(colentity);

					}

				}
			});

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}

		return result;
	}

	public String upload(String tableuuid, Map<String, String> map, List<MainColumn> collist, String fileName,
			MultipartFile file, String user) throws Exception {
		String resultmessage = "";
		if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {

		}
//		Map<String, Object> result = new LinkedHashMap<>();
		MainTable item = maintable.findByTableuuid(tableuuid);
		Integer totalrow = item.getTotalrow();
		try {

			InputStream is = file.getInputStream();
			XSSFWorkbook workbook = new XSSFWorkbook(is);

			int sheets = workbook.getNumberOfSheets();

			Map<String, ArrayList<MainColumn>> temp = new LinkedHashMap<>();

			List<String> rstt = new ArrayList<>();
			MainTable mtitem = maintable.findByTableuuid(tableuuid);
			for (int i = 0; i < sheets; i++) {
				XSSFSheet sheet = workbook.getSheetAt(i);
				int rows = sheet.getPhysicalNumberOfRows();
				String titlename = "";
				String pkname = "";
				String pkcode = "";
				int noOfColumns = sheet.getRow(0).getPhysicalNumberOfCells();// 取得Column

				Map<String, String> jsontemp = new LinkedHashMap<>();
				Map<String, String> pktemp = new LinkedHashMap<>();
				String tablename = "";

				tablename = mtitem.getTablecode();
				XSSFRow titlerow = sheet.getRow(0);

				/*
				 * 底下是比對上傳欄位與資料庫的對應
				 */

				List<String> dbcolname = new ArrayList<>();
				List<String> uploadcolname = new ArrayList<>();
				for (int k = 0; k < noOfColumns; k++) {
					titlename = transToString(titlerow, k).getStringCellValue();
					if (!"".equals(titlename)) {
						uploadcolname.add(titlename);
					}
				}

//				for (MainColumn transcoluuid : collist) {
//					if (!"指定".equals(transcoluuid.getNote()) && !"指定下拉".equals(transcoluuid.getNote())
//							&& !"空白欄位".equals(transcoluuid.getNote())
//							&& !"LicenseID自動代入1".equals(transcoluuid.getNote())
//							&& !"ExchangeSchool下拉階層代入".equals(transcoluuid.getNote())
//							&& !"LicenseID自動代入2".equals(transcoluuid.getNote())
//							&& !"LicenseID下拉".equals(transcoluuid.getNote())
//							&& !"LicenseID自動代入3".equals(transcoluuid.getNote())
//							&& !"ExchangeCollege階層代入".equals(transcoluuid.getNote())
//							&& !"ExchangeSchool階層代入".equals(transcoluuid.getNote())) {
//						dbcolname.add(transcoluuid.getColumncname());
//					}
//
//				}

				for (MainColumn transcoluuid : collist) {
					if ("".equals(transcoluuid.getNote()) || null == transcoluuid.getNote()) {
						dbcolname.add(transcoluuid.getColumncname());
					}
				}
				List<String> where = new ArrayList<>();
				Collections.sort(dbcolname);
				Collections.sort(uploadcolname);

				boolean canienter = true;

				if (dbcolname.size() == uploadcolname.size()) {
					for (int col = 0; col < dbcolname.size(); col++) {
						if (!dbcolname.get(col).equals(uploadcolname.get(col))) {
							dbcolname.get(col);
							uploadcolname.get(col);
							where.add("資料庫欄位為:"+dbcolname.get(col)+";"+"上傳欄位為"+uploadcolname.get(col));
							System.out.println(dbcolname.get(col));
							System.out.println(uploadcolname.get(col));
							canienter = false;
						}
					}
				} else {
					canienter = false;
				}

				if (canienter) {
					for (int j = 1; j < rows; j++) {

						XSSFRow row = sheet.getRow(j);

						for (java.util.Map.Entry<String, String> vo : map.entrySet()) {

							for (MainColumn transcoluuid : collist) {

								if (vo.getKey().equals(transcoluuid.getColumnuuid())) {

									if (transcoluuid.getEncode() != null) {
										if (("*").equals(transcoluuid.getEncode())) {
											titlename = transcoluuid.getColumnename();
											byte[] encode = EncrypAES.encrypt(vo.getValue().trim(), password);
											String code = EncrypAES.parseByte2HexStr(encode);
											jsontemp.put(titlename, code);
										} else {
											titlename = transcoluuid.getColumnename();
											jsontemp.put(titlename, vo.getValue().trim());
										}
									} else {
										titlename = transcoluuid.getColumnename();
										jsontemp.put(titlename, vo.getValue().trim());
									}

									if (("pk").equals(transcoluuid.getPk())) {
										pkname = transcoluuid.getColumnename();

										if ("".equals(pkcode)) {

											pkcode = vo.getValue();
										} else {
											pkcode += "_" + vo.getValue();
										}
									}

								}
							}

						}

						for (int k = 0; k < noOfColumns; k++) {
							String tablevalue = "";
							if (transToString(row, k) != null) {

								for (MainColumn transcoluuid : collist) {
									titlename = transToString(titlerow, k).getStringCellValue();
									tablename = transcoluuid.getTablename();
									if (titlename.equals(transcoluuid.getColumncname())) {

										if (("*").equals(transcoluuid.getEncode())) {
											tablevalue = transToString(row, k).getStringCellValue().trim();
											titlename = transcoluuid.getColumnename();
											byte[] encode = EncrypAES.encrypt(tablevalue, password);
											String code = EncrypAES.parseByte2HexStr(encode);
											jsontemp.put(titlename, code);
										} else {
											tablevalue = transToString(row, k).getStringCellValue().replace("　", "")
													.trim();
											titlename = transcoluuid.getColumnename();
											jsontemp.put(titlename, tablevalue.trim());
										}

										if (("DATE").equals(transcoluuid.getDatatype())) {
											titlename = transcoluuid.getColumnename();
											XSSFCell cell = row.getCell(k);
											String date = "";
											try {

												if (!"".equals(transDate(row, k).getStringCellValue())) {
													if (transcoluuid.getDateinputformat() != null) {
														if ("YYYY/MM/DD".equals(transcoluuid.getDateinputformat())) {
															String rawdate = transToString(row, k).getStringCellValue();
															if (!("").equals(rawdate)
																	&& !StringUtils.isEmpty(rawdate)) {
																String sp[] = rawdate.split("/");
																String year = sp[0];
																String month = sp[1];
																String day = sp[2];
																date = year + "-" + month + "-" + day;
															}

														}
														if ("YYYY/MM/DD HH:MM"
																.equals(transcoluuid.getDateinputformat())) {
															String rawdate = transToString(row, k).getStringCellValue();
															if (!("").equals(rawdate)
																	&& !StringUtils.isEmpty(rawdate)) {
																String sp1[] = rawdate.split(" ");
																String sp[] = sp1[0].split("/");
																String year = sp[0];
																String month = sp[1];
																String day = sp[2];
																date = year + "-" + month + "-" + day;
															}

														}
														
														if ("YYYY-MM-DD HH:MM"
																.equals(transcoluuid.getDateinputformat())) {
															String rawdate = transToString(row, k).getStringCellValue();
															if (!("").equals(rawdate)
																	&& !StringUtils.isEmpty(rawdate)) {
																String sp1[] = rawdate.split(" ");
																String sp[] = sp1[0].split("-");
																String year = sp[0];
																String month = sp[1];
																String day = sp[2];
																date = year + "-" + month + "-" + day;
															}

														}

														if ("YYYMMDD".equals(transcoluuid.getDateinputformat())) {
															String rawdate = transToString(row, k).getStringCellValue();
															if (!("").equals(rawdate)
																	&& !StringUtils.isEmpty(rawdate)) {
																String year = rawdate.substring(0, 3);
																String month = rawdate.substring(4, 5);
																String day = rawdate.substring(6, 7);
																date = year + "-" + month + "-" + day;
															}

														}

													}
												} else {
													date = formatExcelDate(
															Integer.valueOf(transDate(row, k).getStringCellValue()));
												}

											} catch (NumberFormatException e) {
												date = transDate(row, k).getStringCellValue().replace(" ", "");
											}catch(ArrayIndexOutOfBoundsException e) {
												date = transDate(row, k).getStringCellValue().replace(" ", "");
												date = formatExcelDate(Integer.valueOf(date));
											}

											jsontemp.put(titlename, date);
										}

										if (("DATETIME").equals(transcoluuid.getDatatype())) {
											titlename = transcoluuid.getColumnename().trim();

											tablevalue = transToString(row, k).getStringCellValue().trim();
											titlename = transcoluuid.getColumnename();

											int scec = 86400;

											if (!StringUtils.isEmpty(tablevalue)) {

												int ket = (int) (scec * Double.valueOf(tablevalue));

												String resultvalue = secToTime(ket);

												jsontemp.put(titlename, resultvalue);
											}

										}

										if (("FLOAT(1,2)").equals(transcoluuid.getDatatype())) {
											titlename = transcoluuid.getColumnename().trim();

											XSSFCell cell = row.getCell(k);

											Double date = 0.0;
											String str = "";
											try {
												if (!"".equals(transToString(row, k).getStringCellValue())) {
													date = Double.valueOf(transToString(row, k).getStringCellValue());
													str = String.valueOf(date);

												}
											} catch (NumberFormatException e) {
												date = Double.valueOf(transToString(row, k).getStringCellValue());
											}

											jsontemp.put(titlename, str);
										}

										if (("FLOAT(3,2)").equals(transcoluuid.getDatatype())) {
											titlename = transcoluuid.getColumnename().trim();
											XSSFCell cell = row.getCell(k);
											Double date = 0.0;
											String str = "";
											try {
												if (!"".equals(transToString(row, k).getStringCellValue())) {
													date = Double.valueOf(transToString(row, k).getStringCellValue());

//													DecimalFormat df1 = new DecimalFormat("000.00");
													str = String.valueOf(date);

												}
											} catch (NumberFormatException e) {
												date = Double.valueOf(transToString(row, k).getStringCellValue());
											}

											jsontemp.put(titlename, str);
										}

										if (("FLOAT(2,1)").equals(transcoluuid.getDatatype())) {
											titlename = transcoluuid.getColumnename().trim();

											XSSFCell cell = row.getCell(k);
											Double date = 0.0;
											String str = "";
											try {
												if (!"".equals(transToString(row, k).getStringCellValue())) {
													date = Double.valueOf(transToString(row, k).getStringCellValue());

//													DecimalFormat df1 = new DecimalFormat("00.0");
													str = String.valueOf(date);

												}
											} catch (NumberFormatException e) {
												date = Double.valueOf(transToString(row, k).getStringCellValue());
											}

											jsontemp.put(titlename, str);
										}

										if (("FOLAT(3,1)").equals(transcoluuid.getDatatype())) {
											titlename = transcoluuid.getColumnename().trim();

											XSSFCell cell = row.getCell(k);
											Double date = 0.0;
											String str = "";
											try {
												if (!"".equals(transToString(row, k).getStringCellValue())) {
													date = Double.valueOf(transToString(row, k).getStringCellValue());

//													DecimalFormat df1 = new DecimalFormat("000.0");
													str = String.valueOf(date);

												}
											} catch (NumberFormatException e) {
												date = Double.valueOf(transToString(row, k).getStringCellValue());
											}

											jsontemp.put(titlename, str);
										}

										if (("FLOAT(4,2)").equals(transcoluuid.getDatatype())) {
											titlename = transcoluuid.getColumnename().trim();

											XSSFCell cell = row.getCell(k);
											Double date = 0.0;
											String str = "";
											try {
												if (!"".equals(transToString(row, k).getStringCellValue())) {
													date = Double.valueOf(transToString(row, k).getStringCellValue());

//													DecimalFormat df1 = new DecimalFormat("0000.00");
													str = String.valueOf(date);

												}
											} catch (NumberFormatException e) {
												date = Double.valueOf(transToString(row, k).getStringCellValue());
											}

											jsontemp.put(titlename, str);
										}

//										if ("STRING".equals(transcoluuid.getDatatype())) {
//											titlename = transcoluuid.getColumnename().trim();
//											XSSFCell cell = row.getCell(k);
//											Double date = 0.0;
//											String str = "";
//
//											if (!"".equals(transToString(row, k).getStringCellValue())) {
//												str = (transToString(row, k).getStringCellValue());
//
//											}
//
//											jsontemp.put(titlename, str);
//										}

										if (("pk").equals(transcoluuid.getPk())) {
											pkname = transcoluuid.getColumnename();

											if ("".equals(pkcode)) {
												tablevalue = transToString(row, k).getStringCellValue().trim();
												pkcode = tablevalue;
											} else {
												tablevalue = transToString(row, k).getStringCellValue().trim();
												pkcode += "_" + tablevalue;
											}
										}
									}

								}
							}

						}

						JSONObject result = new JSONObject(jsontemp);
						jsontemp = new HashMap<String, String>();
						NuuColumnValue setv = nuucolumnvalue.findByColumnpkvalue(pkcode);

						if (setv == null)
							setv = new NuuColumnValue();
						// NuuColumnValue setv = new NuuColumnValue();
						// setv.setId(pkitem.getId());

						// NuuColumnValue setv = new NuuColumnValue();

						UUID valueuuid = UUID.randomUUID();
						String coluuidsave = valueuuid.toString();
						coluuidsave = coluuidsave.toUpperCase();

						setv.setTablename(tablename);
						setv.setValueuuid(coluuidsave);
						setv.setTableuuid(tableuuid);
						setv.setColumnvalue(result.toString());
						setv.setColumnpkvalue(pkcode);

						totalrow += 1;

						nuucolumnvalue.save(setv);

						resultmessage = "成功匯入文件";
						pkcode = "";
					}

					// 寄信

				} else {
					for(String s :where) {
						resultmessage = s;						
					}
				}
			}
		} catch (

		IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} finally {
			if (("1").equals(item.getIschange())) {
				List<Users> useraccount = usersRepository.findByrole("倉儲資料管理者");
				for (Users us : useraccount) {

					Date now = new Date();
					String title = "校務倉儲資料系統自動通知" + item.getTablecode() + "內容異動";

					String info = item.getTablecode() + " 已於" + now + "經" + user + "修改內容。\r\n" + "\r\n"
							+ "此為系統自動寄發內容，請勿直接回覆，如有疑問請聯繫校務研究辦公室*";

					sendMail(us.getAccount(), info, title);

					Logs logs = new Logs();
					logs.setStartTime(new Date());
					logs.setExeTime(new Date());
					logs.setLogtype("RAWDATA");
					logs.setOperation("寄信給:" + us.getAccount() + "," + "內容:" + item.getTablename() + "已上傳");
					logsRepository.save(logs);
				}
			}

			Date now = new Date();

			MainTable item2 = maintable.findByTableuuid(tableuuid);
			item2.setTotalrow(item.getTotalrow() + totalrow);
			item2.setLastchange(now);
			maintable.save(item2);
		}
		return resultmessage;
	}

	public XSSFCell transToString(XSSFRow row, Integer num) {
		if (row != null) {
			XSSFCell cell = row.getCell(num);
			if (cell != null) {
				cell.setCellType(CellType.STRING);
				return cell;
			} else {
				return null;
			}
		}
		return null;
	}

	public XSSFCell transDate(XSSFRow row, Integer num) {
		if (row != null) {
			XSSFCell cell = row.getCell(num);

			if (cell != null) {

				return cell;

			}
		}
		return null;
	}

	public XSSFCell transToInt(XSSFRow row, Integer num) {
		if (row != null) {
			XSSFCell cell = row.getCell(num);
			if (cell != null) {
				cell.setCellType(CellType.NUMERIC);
				return cell;
			} else {
				return null;
			}
		}
		return null;
	}

	@Scheduled(fixedRate = 60 * 60 * 1000)
	void scheduled() throws Exception {
		Date date = new Date();
		List<MainTable> mtitem = maintable.findAll();
		String title = "";
		String info = "";
		for (MainTable item : mtitem) {
			if (("1").equals(item.getStartupload())) {
				if (("1").equals(item.getStartsend()) && item.getStartdate() != null) {
					Boolean compare = DateUtils.isSameDay(date, item.getStartdate());
					if (compare) {
						title = "校務倉儲資料系統自動通知  " + item.getTablecode() + "編輯日程已開始";

						info = item.getTablecode() + "編輯日程已開始，預計將於" + item.getEnddate() + "結束。\r\n" + "請盡速填報相關內容 \r\n"
								+ "此為系統自動寄發內容，請勿直接回覆，如有疑問請聯繫校務研究辦公室";
						sendMail(item.getAuthName(), info, title);
					}
				}

				if (("1").equals(item.getEndsend()) && item.getEnddate() != null) {
					Boolean compare = DateUtils.isSameDay(date, item.getEnddate());
					if (compare) {
						title = "校務倉儲資料系統自動通知  " + item.getTablecode() + "編輯日程已結束";

						info = item.getTablecode() + "編輯日程已結束\r\n" + "此為系統自動寄發內容，請勿直接回覆，如有疑問請聯繫校務研究辦公室";
						sendMail(item.getAuthName(), info, title);
					}
				}
			}
		}
	}

	void sendMail(String auth[], String mailinfo, String title) throws GeneralSecurityException {
		for (String item : auth) {
			List<Users> usitem = usersRepository.findByrole(item);
			for (Users sendmain : usitem) {
				MainSend sendpa = new MainSend();
				sendpa.send(sendmain.getAccount(), mailinfo, title);
			}
		}
		List<Users> useraccount = usersRepository.findByrole("倉儲資料管理者");
		for (Users us : useraccount) {
			MainSend sendpa = new MainSend();
			sendpa.send(us.getAccount(), mailinfo, title);
		}
	}

	void sendMail(String auth, String mailinfo, String title) throws GeneralSecurityException {

		MainSend sendpa = new MainSend();
		sendpa.send(auth, mailinfo, title);

	}

	public Pageable pageable(Integer pageno, Integer pagesize, Map<String, String> item) {
		Pageable pageable = null;
		if (item != null) {
			if (item.get("order").equals("asc")) {
				Sort sortitem = new Sort(Direction.ASC, item.get("name"));
				pageable = PageRequest.of(pageno, pagesize, sortitem);
			} else if (item.get("order").equals("desc")) {
				Sort sortitem = new Sort(Direction.DESC, item.get("name"));
				pageable = PageRequest.of(pageno, pagesize, sortitem);
			}
		} else {
			pageable = PageRequest.of(pageno, pagesize);
		}

		return pageable;
	}

	private String formatExcelDate(int day) {
		Calendar calendar = new GregorianCalendar(1900, 0, -1);
		Date gregorianDate = calendar.getTime();
		String formatExcelDate = ExcelDateUtils.format(ExcelDateUtils.addDay(gregorianDate, day),
				ExcelDateUtils.YYYYMMDD);
		return formatExcelDate;
	}

	private String formatExcelDateTime(String day) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date formatExcelDate = ExcelDateUtils.parse(day);
		String dateString = sdf.format(formatExcelDate);
		return dateString;
	}

	public String secToTime(int time) {
		String timeStr = null;
		int hour = 0;
		int minute = 0;
		int second = 0;
		if (time <= 0)
			return "00:00";
		else {
			minute = time / 60;
			if (minute < 60) {
				second = time % 60;
				timeStr = unitFormat(minute) + ":" + unitFormat(second);
			} else {
				hour = minute / 60;
				if (hour > 99)
					return "99:59:59";
				minute = minute % 60;
				second = time - hour * 3600 - minute * 60;
				timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
			}
		}
		return timeStr;
	}

	public static String unitFormat(int i) {
		String retStr = null;
		if (i >= 0 && i < 10)
			retStr = "0" + Integer.toString(i);
		else
			retStr = "" + i;
		return retStr;
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

	public Specification<MainTable> buildFilters(List<Map<String, String>> filters, String role) {

		Specification<MainTable> specification = new Specification<MainTable>() {
			@Override
			public Predicate toPredicate(Root<MainTable> root, CriteriaQuery<?> query,
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
				if (!StringUtils.isEmpty(role) && !("").equals(role)) {
					if (!("系統管理者").equals(role)) {
						Predicate authName = criteriaBuilder.like(root.get("authName"), '%' + role + '%');
						predicate.add(authName);
					}
				}

				Predicate[] predicates = new Predicate[predicate.size()];

				return criteriaBuilder.and(predicate.toArray(predicates));
			}
		};
		return specification;

	}

}
