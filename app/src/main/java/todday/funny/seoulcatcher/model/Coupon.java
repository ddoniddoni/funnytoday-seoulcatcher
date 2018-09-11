package todday.funny.seoulcatcher.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Coupon implements Parcelable {
    private String name;
    private String logoUrl;
    private String color;
    private int discountRate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(int discountRate) {
        this.discountRate = discountRate;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.logoUrl);
        dest.writeInt(this.discountRate);
    }

    public Coupon() {
    }

    protected Coupon(Parcel in) {
        this.name = in.readString();
        this.logoUrl = in.readString();
        this.discountRate = in.readInt();
    }

    public static final Parcelable.Creator<Coupon> CREATOR = new Parcelable.Creator<Coupon>() {
        @Override
        public Coupon createFromParcel(Parcel source) {
            return new Coupon(source);
        }

        @Override
        public Coupon[] newArray(int size) {
            return new Coupon[size];
        }
    };
}
