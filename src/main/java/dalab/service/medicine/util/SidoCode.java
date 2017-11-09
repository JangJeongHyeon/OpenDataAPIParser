package dalab.service.medicine.util;

/**
 * Created by Jang Jeong Hyeon on 2017-09-27.
 */
public enum SidoCode {
    Seoul("110000","서울"),
    Busan("210000","부산"),
    Incheon("220000","인천"),
    Daegu("230000","대구"),
    Gwangju("240000","광주"),
    Daejeon("250000","대전"),
    Ulsan("260000","울산"),
    Gyeonggi("310000","경기"),
    Gangwon("320000","강원"),
    Chungbuk("330000","충북"),
    Chungnam("34000","충남"),
    Jeonbuk("350000","전북"),
    Jeonnam("360000","전남"),
    Gyeongbuk("370000","경북"),
    Gyeongnam("380000","경남"),
    Jeju("390000","제죽"),
    Sejong("410000","세종");

    final private String sidoCode;
    final private String sidoName;

    SidoCode(String sidoCode, String sidoName) {
        this.sidoCode = sidoCode;
        this.sidoName = sidoName;
    }

    public String getSidoCode(){
        return this.sidoCode;
    }

    public String getSidoName(){
        return this.sidoName;
    }
}
