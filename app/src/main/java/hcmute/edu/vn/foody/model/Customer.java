package hcmute.edu.vn.foody.model;

public class Customer {
    private int CusID;
    private int AccID;
    private String CusName;
    private int CusAge;
    private String CusPhoneNB;
    private byte[] CusImg;

    public Customer(int cusID,int AccID, String cusName, int cusAge, String cusPhoneNB, byte[] cusImg) {
        CusID = cusID;
        CusName = cusName;
        CusAge = cusAge;
        CusPhoneNB = cusPhoneNB;
        CusImg = cusImg;
        AccID = AccID;
    }

    public int getAccID() {
        return AccID;
    }

    public void setAccID(int accID) {
        AccID = accID;
    }

    public int getCusID() {
        return CusID;
    }

    public void setCusID(int cusID) {
        CusID = cusID;
    }

    public String getCusName() {
        return CusName;
    }

    public void setCusName(String cusName) {
        CusName = cusName;
    }

    public int getCusAge() {
        return CusAge;
    }

    public void setCusAge(int cusAge) {
        CusAge = cusAge;
    }

    public String getCusPhoneNB() {
        return CusPhoneNB;
    }

    public void setCusPhoneNB(String cusPhoneNB) {
        CusPhoneNB = cusPhoneNB;
    }

    public byte[] getCusImg() {
        return CusImg;
    }

    public void setCusImg(byte[] cusImg) {
        CusImg = cusImg;
    }
}
