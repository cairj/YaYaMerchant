package com.yaya.merchant.net;

public class Urls {

    private static final String HOST = "https://mall.91yayagou.com.cn/index.php/app/";

    public static final String LOGIN = HOST + "Login/login";
    public static final String SEND_MESSAGE = HOST + "Login/getVerificationCode";
    public static final String VERIFICATION_CODE = HOST + "Login/verificationCode";
    public static final String RESET_PASSWORD = HOST + "Login/changePassword";
    public static final String REGISTER_MERCHAT = HOST + "Shop/Apply";
    public static final String HOME_DATA = HOST + "api/Homeindex/Index";
    public static final String BILL_HOUSTON = HOST + "api/bill/Houston";
    public static final String BILL_GET_ALL_STORE = HOST + "api/bill/GetAllStore";
    public static final String BILL_GET_MEMBER_BILL = HOST + "api/bill/BillMember";
    public static final String GET_MEMBER_MANAGER_LIST = HOST + "api/Homeindex/MemberManager";
    public static final String GET_MEMBER_DATA = HOST + "api/Homeindex/DataStatistics";
    public static final String GET_BANK_CARD = HOST + "api/Cashout/GetBankCard";
    public static final String GET_MEMBER_BALANCE = HOST + "api/Cashout/GetAmount";
    public static final String WITH_DRAW_MONEY = HOST + "api/Cashout/CreateCashout";
    public static final String GET_WITH_DRAW_MONEY_RECORD = HOST + "api/Cashout/GetList";
    public static final String USER_DATA = HOST + "Personal/personal";
    public static final String GET_MERCHANT_LIST = HOST + "Stores/stores";
    public static final String GET_EMPLOYEE_LIST = HOST + "api/SystemUser/EmployeeManager";
    public static final String GET_MERCHANT_QR_CODE = HOST + "Code/Code";
    public static final String GET_BIND_INFO = HOST + "Authentication/authentication";
    public static final String GET_INFORMATION = HOST + "api/SystemUser/BasicInfo";
    public static final String SET_VOICE_INDEX = HOST + "Voice/voice";
    public static final String SET_VOICE = HOST + "Voice/voices";
    public static final String SET_VOICE_SOUND = HOST + "Voice/sounds";
    public static final String SET_VOICE_PUSH = HOST + "Voice/letter";
    public static final String GET_MERCHANT_VOICE_INDEX = HOST + "api/SystemUser/VoiceStoreSet";
    public static final String SET_MERCHANT_VOICE = HOST + "api/SystemUser/VoiceStoreUpdate";
    public static final String GET_JPUSH_TAG_ALIAS = HOST + "api/Account/GetUserInfo";
    public static final String UPLOAD_IMG_FILES = HOST + "Upload/avatorUpload";
    public static final String UPLOAD_IMG_PICS = HOST + "Upload/Uploadpics";
    public static final String PUSH_FEED_BACK = HOST + "SystemUser/Feedback";
    public static final String VERIFICATION_INDEX = HOST + "api/SystemUser/VerificationIndex";
    public static final String VERIFICATION_SET = HOST + "api/SystemUser/VerificationSet";
    public static final String GET_SERVICE_PHONE = HOST + "Login/GetPhone";
    public static final String CHANGE_PROFILE_PIC = HOST + "Login/modifyHeadImg";
    public static final String CHANGE_PASSWORD = HOST + "Login/changePwd";
    public static final String BILL_GET_RECONCILIATION = HOST + "api/bill/Reconciliation";
    public static final String BILL_GET_BILL_LIST = HOST + "api/bill/BillList";
    public static final String GET_ORDER_DATA = HOST + "api/ShopOrder/Index";
    public static final String GET_ORDER_LIST = HOST + "api/ShopOrder/OrderList";
    public static final String GET_DELIVER_ORDER_LIST = HOST + "api/ShopOrder/DeliverGoodsList";
    public static final String GET_REFUND_ORDER_LIST = HOST + "api/ShopOrder/RefundApplyList";
    public static final String GET_ORDER_DETAIL = HOST + "api/ShopOrder/OrderDetail";
    public static final String GET_DELIVER_ORDER_DETAIL = HOST + "api/ShopOrder/DeliverGoodsDetail";
    public static final String GET_REFUND_ORDER_DETAIL = HOST + "api/ShopOrder/RefundApplyDetail";
    public static final String GET_EXPRESS_COMPANY_LIST = HOST + "api/ShopOrder/GetExpressName";
    public static final String DELIVER_GOODS = HOST + "ShopOrder/DeliverGoodsSet";
    public static final String ALLOW_REFUND = HOST + "ShopOrder/RefundSuccess";
    public static final String DISALLOW_REFUND = HOST + "ShopOrder/RefundFail";
    public static final String GET_ARTICLE_LIST = HOST + "api/SystemUser/ArticleList";
    public static final String GET_HOUSTON_DETAIL = HOST + "api/bill/HoustonDetail";
    public static final String GET_MEMBER_DETAIL = HOST + "api/bill/BillMemberDetail";
    public static final String GET_CONTRACT_INFORMATION = HOST + "Commission/commission";
    public static final String GET_MERCHANT_INFO = HOST + "Merchant/merchant";
    public static final String GET_MERCHANT_LIST_BY_AGENT = HOST + "Business/business";
    public static final String GET_MERCHANT_DETAIL = HOST + "Data/datas";
    public static final String GET_MERCHANT_CLASSIFY = HOST + "Business/cate";

}
