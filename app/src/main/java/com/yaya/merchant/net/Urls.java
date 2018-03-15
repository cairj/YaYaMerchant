package com.yaya.merchant.net;

public class Urls {

    private static final String HOST = "http://api.xinghezhijia.com/";

    public static final String LOGIN = HOST + "api/Account";
    public static final String GET_PHONE_BY_USER = HOST + "api/Account/GetUserPhone";
    public static final String SEND_MESSAGE = HOST + "api/Account/SendMessage";
    public static final String HOME_DATA = HOST + "api/Homeindex/Index";
    public static final String BILL_HOUSTON = HOST + "api/bill/Houston";
    public static final String BILL_GET_ALL_STORE = HOST + "api/bill/GetAllStore";
    public static final String BILL_GET_MEMBER_BILL = HOST + "api/bill/BillMember";
    public static final String GET_MEMBER_MANAGER_LIST = HOST + "api/Homeindex/MemberManager";
    public static final String GET_BANK_CARD = HOST + "api/Cashout/GetBankCard";
    public static final String GET_MEMBER_BALANCE = HOST + "api/Cashout/GetAmount";
    public static final String WITH_DRAW_MONEY = HOST + "api/Cashout/CreateCashout";
    public static final String GET_WITH_DRAW_MONEY_RECORD = HOST + "api/Cashout/GetList";
    public static final String USER_DATA = HOST + "api/SystemUser/UserInfoIndex";
    public static final String GET_MERCHANT_LIST = HOST + "api/SystemUser/StoreManager";

}
