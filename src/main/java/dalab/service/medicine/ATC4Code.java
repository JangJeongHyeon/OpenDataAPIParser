package dalab.service.medicine;

import dalab.common.Pair;
import dalab.dao.medicine.ATC4CodeDao;
import dalab.service.Service;
import dalab.vo.ATC4StepAPIVo;
import dalab.vo.SGISCode;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Jang Jeong Hyeon on 2017-09-14.
 */
public class ATC4Code implements Service {
    protected final ATC4CodeDao dao = new ATC4CodeDao();
    protected String years[] = {"2010", "2011", "2012", "2013", "2014","2015", "2016", "2017"};
//    protected String year = "2015";
    protected String months[] = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    protected final ArrayList<SGISCode> sgisCode = dao.getSgisCode();
    protected final ArrayList<Pair<String, String>> atc = dao.getATCInfo();


    // Request message filed variables (static value)
    protected final String insuranceType = "0";
    protected final String cpmdPrscTp = "02";
    protected final String numberOfRows = "100";
    protected final String pageNo = "1";

    public void run(HttpClient client) {
        for (String year : years) {
            for (String month : months) {
                for (SGISCode data : sgisCode) {
                    for (Pair<String, String> atc : atc) {
                        getAtcStep4Medicine(client, year + month, data.getSidoCode(), data.getSigunguCode(), insuranceType, cpmdPrscTp, numberOfRows, pageNo, atc.getFirst());
                    }
                }
            }
        }
    }


    private void getAtcStep4Medicine(HttpClient client, final String diagYm, final String sidoCd, final String sgguCd, final String insuranceType, final String cpmdPrscTp, final String numberOfRows, final String pageNo, final String atcCode) {
        try {

            // Rest api service url
            StringBuilder builder = new StringBuilder("http://apis.data.go.kr/B551182/msupUserInfoService/getAtcStp4AreaList");
            // set api key
            builder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=uKoS2h8nLZtyARxcnTC64ywVHm%2Fh0RG27jMg2mdabNVJyU%2FdnmUVRBMnMCk6stklk9ZBwAKmMtXtRxiyaHRGTg%3D%3D");

            // Request Query build
            builder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode(numberOfRows, "UTF-8"));
            builder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode(pageNo, "UTF-8"));
            builder.append("&" + URLEncoder.encode("diagYm", "UTF-8") + "=" + URLEncoder.encode(diagYm, "UTF-8"));
            builder.append("&" + URLEncoder.encode("atcStep4Cd", "UTF-8") + "=" + URLEncoder.encode(atcCode, "UTF-8"));
            builder.append("&" + URLEncoder.encode("insupTp", "UTF-8") + "=" + URLEncoder.encode(insuranceType, "UTF-8"));
            builder.append("&" + URLEncoder.encode("cpmdPrscTp", "UTF-8") + "=" + URLEncoder.encode(cpmdPrscTp, "UTF-8"));
            builder.append("&" + URLEncoder.encode("sidoCd", "UTF-8") + "=" + URLEncoder.encode(sidoCd, "UTF-8"));
            builder.append("&" + URLEncoder.encode("sgguCd", "UTF-8") + "=" + URLEncoder.encode(sgguCd, "UTF-8"));

            // Assign request query to httpGet Method
            HttpGet request = new HttpGet(builder.toString());

            // execute request query and receive response entity
            HttpEntity response = client.execute(request).getEntity();
            // Convert response entity to String type and parsing
            Document doc = Jsoup.parse(String.format(EntityUtils.toString(response, "UTF-8")));

            // Parsing data item
            Elements elements = doc.select("body").select("items").select("item");

            for (Element data : elements) {
                System.out.println(data.toString());
                ATC4StepAPIVo vo = new ATC4StepAPIVo();
                vo.setAtcCode(data.select("atcStep4Cd").text());
                vo.setAtcCodeName(data.select("atcStep4CdNm").text());
                vo.setDate(data.select("diagYm").text());
                vo.setMoneyOfUseAmount(Integer.parseInt(data.select("msupUseAmt").text()));
                vo.setInsuranceType(Integer.parseInt(data.select("insupTpCd").text()));
                vo.setRecuClcd(Integer.parseInt(data.select("recuclcd").text()));
                vo.setSgguCd(Integer.parseInt(data.select("sgguCd").text()));
                vo.setSgguCdNm(data.select("sgguCdNm").text());
                vo.setSidoCdNm(data.select("sidoCdNm").text());
                vo.setTotalUseQuantity(Integer.parseInt(data.select("totUseQty").text()));
                System.out.println("=========================================================================");
                System.out.println(data.text());
                System.out.println("=========================================================================");
                dao.save(vo);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
