package dalab.service.population;

import dalab.dao.population.PopulationDao;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import dalab.service.Service;
import dalab.vo.PopulationVo;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Jang Jeong Hyeon on 2017-08-07.
 */
public class SearchPopulation implements Service {
    protected final PopulationDao dao = new PopulationDao();
    protected String years[] = {"2000", "2005", "2010", "2015"};
    protected final ArrayList<String> sgisCode = dao.getSgisCode();
    protected String gender[] = {"0", "1", "2"};
    protected final ArrayList<String> ageCode = dao.getAgeType();

    public void run(HttpClient client) {
        try {
            // get access token for using api
            for (String year : years) {
                for (String sex : gender) {
                    for (String age : ageCode) {
                        String accessToken = getAccessToken(client);
                        getPopulationEachAge(accessToken, year, sex, age, client);
                    }
//                    getLocalGenderPouplation(accessToken, year, sex, client);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getAccessToken(HttpClient client) throws IOException {
        String getTokenURL = String.format("http://sgisapi.kostat.go.kr/OpenAPI3/auth/authentication.json?consumer_key=%s&consumer_secret=%s", URLEncoder.encode("4c03bd077e3843ae81f9", "UTF-8"), URLEncoder.encode("6bcf9b7d32bc4fde931a", "UTF-8"));
        HttpGet getAcceessToken = new HttpGet(getTokenURL);
        HttpEntity response = client.execute(getAcceessToken).getEntity();
        JSONObject getToken = new JSONObject(EntityUtils.toString(response));
        return getToken.getJSONObject("result").get("accessToken").toString();
    }

//    private void getLocalPopulation(final String token, final String year, HttpClient client) throws IOException {
//
//        for (String code : sgisCode) {
//            PopulationVo dalab.vo = new PopulationVo();
//            String query = String.format("http://sgisapi.kostat.go.kr/OpenAPI3/stats/searchpopulation.json?year=%s&gender=0&adm_cd=%s&bnd_year=%s&accessToken=%s", URLEncoder.encode(year, "UTF-8"), URLEncoder.encode(code, "UTF-8"), URLEncoder.encode(year, "UTF-8"), URLEncoder.encode(token, "UTF-8"));
//            HttpGet request = new HttpGet(query);
//            HttpEntity response = client.execute(request).getEntity();
//            JSONObject data = new JSONObject(EntityUtils.toString(response)).getJSONArray("result").getJSONObject(0);
//            // add data to value object
//            dalab.vo.setAdmCode(Integer.valueOf(data.getString("adm_cd")));
//            String admName = data.getString("adm_nm");
//            String siName = "";
//            // TODO 2017-08-09 : '시 군구' 일때 시 분리 알고리즘 추가 필요
//
//            if (admName.contains(" ")) {
//                siName = admName.split(" ")[0];
//            } else {
//                siName = admName;
//            }
//
//            dalab.vo.setAdmName(siName);
//            dalab.vo.setGender(0);
//            dalab.vo.setAgeType(0);
//            dalab.vo.setYear(Integer.valueOf(year));
//            dalab.vo.setAvgAge(Float.valueOf(data.getString("avg_age")));
//            dalab.vo.setPopulation(Integer.valueOf(data.getString("population")));
//            dalab.dao.save(dalab.vo);
//        }
//    }

    public void getLocalGenderPouplation(final String token, final String year, final String gender, HttpClient client) throws IOException {
        for (String code : sgisCode) {
            PopulationVo vo = new PopulationVo();
            String query = String.format("http://sgisapi.kostat.go.kr/OpenAPI3/stats/searchpopulation.json?year=%s&gender=%s&adm_cd=%s&bnd_year=%s&accessToken=%s", URLEncoder.encode(year, "UTF-8"), URLEncoder.encode(gender, "UTF-8"), URLEncoder.encode(code, "UTF-8"), URLEncoder.encode(year, "UTF-8"), URLEncoder.encode(token, "UTF-8"));
            HttpGet request = new HttpGet(query);

            System.out.println("=====================================================================");
            System.out.println("Query : " + query);
            HttpEntity response = client.execute(request).getEntity();
            JSONObject data = new JSONObject(EntityUtils.toString(response)).getJSONArray("result").getJSONObject(0);
            vo.setAdmCode(Integer.valueOf(data.getString("adm_cd")));
            String admName = data.getString("adm_nm");
            String siName = "";

            if (admName.contains(" ")) {
                siName = admName.split(" ")[0];
            } else {
                siName = admName;
            }

            vo.setAdmName(siName);
            vo.setGender(Integer.valueOf(gender));
            vo.setAgeType(0);
            vo.setYear(Integer.valueOf(year));
            vo.setAvgAge(Float.valueOf(data.getString("avg_age")));
            vo.setPopulation(Integer.valueOf(data.getString("population")));
            dao.save(vo);
            System.out.println(vo.toString());
            System.out.println("=====================================================================");
        }
    }

    // TODO 2017-08-09 : 연도별 성별별 연령대별 지역별 인구 구하기 구현

    public void getPopulationEachAge(final String token, final String year, final String gender, final String ageCode, HttpClient client) throws IOException {
        for (String code : sgisCode) {
            PopulationVo vo = new PopulationVo();
            String query = String.format("http://sgisapi.kostat.go.kr/OpenAPI3/stats/searchpopulation.json?year=%s&gender=%s&adm_cd=%s&bnd_year=%s&age_type=%s&accessToken=%s", URLEncoder.encode(year, "UTF-8"), URLEncoder.encode(gender, "UTF-8"), URLEncoder.encode(code, "UTF-8"), URLEncoder.encode(year, "UTF-8"), URLEncoder.encode(ageCode, "UTF-8"), URLEncoder.encode(token, "UTF-8"));
            HttpGet request = new HttpGet(query);
            System.out.println("=====================================================================");
            System.out.println("Query : " + query);
            HttpEntity response = client.execute(request).getEntity();

            JSONObject data;
            try {
                data = new JSONObject(EntityUtils.toString(response)).getJSONArray("result").getJSONObject(0);
                vo.setAdmCode(Integer.valueOf(data.getString("adm_cd")));
                String admName = data.getString("adm_nm");
                String siName = "";

                if (admName.contains(" ")) {
                    siName = admName.split(" ")[0];
                } else {
                    siName = admName;
                }
                vo.setAdmName(siName);
                vo.setGender(Integer.valueOf(gender));
                vo.setAgeType(Integer.parseInt(ageCode));
                vo.setYear(Integer.valueOf(year));
                vo.setAvgAge(Float.valueOf(data.getString("avg_age")));
                Integer population;
                if (data.getString("population").equals("N/A")) {
                    population = 100000000;
                } else {
                    population = Integer.valueOf(data.getString("population"));
                }
                vo.setPopulation(population);

            } catch (Exception e) {
                e.printStackTrace();
                vo.setAdmName("fail");
                vo.setAdmCode(Integer.valueOf(code));
                vo.setGender(Integer.valueOf(gender));
                vo.setAgeType(Integer.parseInt(ageCode));
                vo.setYear(Integer.valueOf(year));
                vo.setAvgAge(0.0f);
                vo.setPopulation(0);
            }


            dao.save(vo);
            System.out.println(vo.toString());
            System.out.println("=====================================================================");
        }
    }

}
