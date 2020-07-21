package controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import logic.ShopService;

@RestController // @ResponseBody: View 없이 직접 데이터를 클라이언트에 전송
@RequestMapping("ajax")
public class AjaxController {

	@Autowired
	ShopService service;
	
	@RequestMapping(value = "graph1", produces = "text/plain; charset=UTF-8")
	public String graph1() {
		System.out.println("ajax");
		Map <String, Object> map = service.graph1();
		System.out.println(map);
		StringBuilder sb = new StringBuilder("[");
		int i = 0;
		for(Map.Entry<String, Object> me : map.entrySet()) {
			sb.append("{\"name\":\""+me.getKey()+"\",");
			sb.append("\"cnt\":\""+me.getValue()+"\"}");
			i++;
			if (i < map.size()) sb.append(",");
		}
		sb.append("]");
		System.out.println(sb);
		return sb.toString();
	}
	
	@RequestMapping(value = "graph2", produces = "text/plain; charset=UTF-8")
	public String graph2() {
		System.out.println("ajax");
		Map <String, Object> map = service.graph2();
		System.out.println(map);
		StringBuilder sb = new StringBuilder("[");
		int i = 0;
		for(Map.Entry<String, Object> me : map.entrySet()) {
			sb.append("{\"regdate\":\""+me.getKey()+"\",");
			sb.append("\"cnt\":\""+me.getValue()+"\"}");
			i++;
			if (i < map.size()) sb.append(",");
		}
		sb.append("]");
		System.out.println(sb);
		return sb.toString();
	}
	
	@RequestMapping(value = "exchange1", produces = "text/html; charset=UTF-8")
	public String exchange1() {
		System.out.println("ajax");
		String url = "https://www.koreaexim.go.kr/site/program/financial/exchange?menuid=001001004002001";
		List <String> list = new ArrayList<String>();
		List <String> list2 = new ArrayList<String>();
		try{
			Document doc = Jsoup.connect(url).get();
			Elements e1 = doc.select(".tc"); // 국가코드, 환율값 태그 선택
			Elements e2 = doc.select(".tl2.bdl"); // 국가이름
			for (int i = 0; i < e1.size(); i++) {
				if(e1.get(i).html().equals("USD")) { // USD 정보 저장
					list.add(e1.get(i).html());
					for (int j = 1; j <= 6; j++) {
						list.add(e1.get(i+j).html());
					}
					break;
				}
			}
			for (Element ele : e2) {
				if(ele.html().contains("미국")) list2.add(ele.html());
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		
		String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		StringBuilder sb = new StringBuilder("[");
		sb.append("<table>");
		sb.append("<caption>수출입은행:"+ today+"</caption>");
		sb.append("<tr><td colspan='3'>"+list2.get(0)+":"+list.get(0)+"</td></tr>");
		sb.append("<tr><th>기준율</th><th>받을때</th><th>파실때</th></tr>");
		sb.append("<tr><td>"+list.get(3)+"</td>");
		sb.append("<td>"+list.get(1)+"</td>");
		sb.append("<td>"+list.get(2)+"</td></tr>");
		sb.append("</table>");
		System.out.println(sb);
		return sb.toString();
	}
	
	@RequestMapping(value = "exchange2", produces = "text/html; charset=UTF-8")
	public String exchange2() {
		System.out.println("ajax");
		Map <String, Object> map = new HashMap<String, Object>();
		StringBuilder sb = new StringBuilder();

		try {
			String kebhana = Jsoup.connect("http://fx.kebhana.com/FER1101M.web").get().select("body").text();
			String strJson = kebhana.substring(kebhana.indexOf("{"));
			JSONParser jsonParser = new JSONParser();
			JSONObject json = (JSONObject) jsonParser.parse(strJson.trim());
			
			JSONArray array = (JSONArray)json.get("리스트"); // String을 json 객체 변경
			System.out.println(array);
			
			float usd = 0f;
			float jpy = 0f;
			ArrayList <String> list = new ArrayList<String>();
			for(int i = 0; i < array.size(); i++) {
				JSONObject obj = (JSONObject)array.get(i); // Array의 요소 1개 리턴
				// JSONObject : json 형태의 객체 {"key": "value", ...}
				// 통화명 : json 형태의 key
				if(obj.get("통화명").toString().equals("미국 USD")) {
					list.add("미국 USD");
					list.add(obj.get("매매기준율").toString());
					list.add(obj.get("현찰사실때").toString());
					list.add(obj.get("현찰파실때").toString());
				}else if (obj.get("통화명").toString().equals("일본 JPY 100")) {
					list.add("일본 JPY 100");
					list.add(obj.get("매매기준율").toString());
					list.add(obj.get("현찰사실때").toString());
					list.add(obj.get("현찰파실때").toString());
				}else if (obj.get("통화명").toString().equals("유로 EUR")) {
					list.add("유로 EUR");
					list.add(obj.get("매매기준율").toString());
					list.add(obj.get("현찰사실때").toString());
					list.add(obj.get("현찰파실때").toString());
				}else if (obj.get("통화명").toString().equals("중국 CNY")) {
					list.add("중국 CNY");
					list.add(obj.get("매매기준율").toString());
					list.add(obj.get("현찰사실때").toString());
					list.add(obj.get("현찰파실때").toString());
				}
			}
		
		sb.append("<table>");
		sb.append("<caption>하나은행:"+ json.get("날짜").toString()+"</caption>");
		sb.append("<tr><th>기준율</th><th>받을때</th><th>파실때</th></tr>");
		sb.append("</table>");
		System.out.println(sb);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}
