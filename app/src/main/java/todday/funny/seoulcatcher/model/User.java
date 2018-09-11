package todday.funny.seoulcatcher.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.auth.FirebaseUser;

import todday.funny.seoulcatcher.util.Keys;

public class User implements Parcelable {
    private String id;
    private String name;
    private String message;
    private String email;
    private String phoneNumber;
    private String photoUrl;

    public void setUser(FirebaseUser firebaseUser) {
        setId(firebaseUser.getUid());
        setEmail(firebaseUser.getEmail());
        setPhoneNumber(firebaseUser.getPhoneNumber());
        Uri uri = firebaseUser.getPhotoUrl();
        if (uri != null) {
            setPhotoUrl(String.valueOf(firebaseUser.getPhotoUrl()));
        } else {
            setPhotoUrl(Keys.DEFAULT_USER_PROFILE_URL);
        }
        if (email != null && email.length() > 0) {
            String[] split = email.split("@");
            for (String s : split) {
                setName(s);
                break;
            }
        }
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.message);
        dest.writeString(this.email);
        dest.writeString(this.phoneNumber);
        dest.writeString(this.photoUrl);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.message = in.readString();
        this.email = in.readString();
        this.phoneNumber = in.readString();
        this.photoUrl = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
