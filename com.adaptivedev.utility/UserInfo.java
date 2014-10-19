package com.adaptivedev.utility;

/**
 * Created by rover on 3/31/14.
 */
public class UserInfo extends Jw {
    public String m_user_name;
    public String m_device_id;
    //public String m_user_id;
    //public double m_balance;
    //public int m_points;
    //JSONObject m_ratingsJo;
    private static final UserInfo INSTANCE = new UserInfo();

    public static UserInfo getInstance() {
        return INSTANCE;
    }

    // Private constructor prevents instantiation from other classes
    private UserInfo()
    {
        //m_balance = -1;
        //m_points  = -1;
    }

}
