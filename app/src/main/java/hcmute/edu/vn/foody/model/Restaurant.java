package hcmute.edu.vn.foody.model;

public class Restaurant {
    private int ResID;
    private String ResName;
    private byte[] ResImg;
    private String ResAddress;

    public Restaurant(int resID, String resName, byte[] resImg, String resAddress) {
        ResID = resID;
        ResName = resName;
        ResImg = resImg;
        ResAddress = resAddress;
    }

    public int getResID() {
        return ResID;
    }

    public void setResID(int resID) {
        ResID = resID;
    }


    public String getResName() {
        return ResName;
    }

    public void setResName(String resName) {
        ResName = resName;
    }

    public byte[] getResImg() {
        return ResImg;
    }

    public void setResImg(byte[] resImg) {
        ResImg = resImg;
    }

    public String getResAddress() {
        return ResAddress;
    }

    public void setResAddress(String resAddress) {
        ResAddress = resAddress;
    }
}
