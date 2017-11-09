package dalab.service.medicine;

import dalab.dao.medicine.MedicineAreaDao;
import dalab.service.Service;
import dalab.service.medicine.util.SidoCode;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URLEncoder;

/**
 * Created by Jang Jeong Hyeon on 2017-09-27.
 */
public class AddressCode implements Service {
    MedicineAreaDao dao = new MedicineAreaDao();
    public void run(HttpClient client) {
        for (SidoCode code : SidoCode.values()) {
            getaddressCode(client, code);
        }
    }

    public void getaddressCode(HttpClient client, SidoCode sido) {
        String sidoCode = sido.getSidoCode();
        String sidoName = sido.getSidoName();

        try {
            StringBuilder builder = new StringBuilder("http://apis.data.go.kr/B551182/codeInfoService/getAddrCodeList");
            builder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=uKoS2h8nLZtyARxcnTC64ywVHm%2Fh0RG27jMg2mdabNVJyU%2FdnmUVRBMnMCk6stklk9ZBwAKmMtXtRxiyaHRGTg%3D%3D");
            builder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("100000", "UTF-8"));
            builder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
            builder.append("&" + URLEncoder.encode("addrTp", "UTF-8") + "=" + URLEncoder.encode("2", "UTF-8"));
            builder.append("&" + URLEncoder.encode("sidoCd", "UTF-8") + "=" + URLEncoder.encode(sidoCode, "UTF-8"));

            HttpGet request = new HttpGet(builder.toString());
            HttpEntity response = client.execute(request).getEntity();
            Document doc = Jsoup.parse(String.format(EntityUtils.toString(response, "UTF-8")));
            Elements elements = doc.select("body").select("items").select("item");

            for (Element data : elements) {
                String addrCd = data.select("addrCd").text();
                String addrCdNm = data.select("addrCdNm").text();
                System.out.println("===================================");
                System.out.println("시도코드: " + addrCd);
                System.out.println("시도명: " + addrCdNm);
                dao.saveAddressInfo(sidoCode,sidoName,addrCd,addrCdNm);
                System.out.println("===================================");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
