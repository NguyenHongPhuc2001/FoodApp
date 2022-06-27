package hcmute.edu.vn.foody.model;

public class Advertisement {
    private int AdID;
    private byte[] AdImg;

    public Advertisement(int adID, byte[] adImg) {
        AdID = adID;
        AdImg = adImg;
    }

    public int getAdID() {
        return AdID;
    }

    public void setAdID(int adID) {
        AdID = adID;
    }

    public byte[] getAdImg() {
        return AdImg;
    }

    public void setAdImg(byte[] adImg) {
        AdImg = adImg;
    }
}

