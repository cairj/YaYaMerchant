package com.yaya.merchant.net;

import com.yaya.merchant.application.MerchantApplication;
import com.yaya.merchant.util.ManifestReader;

public class Urls {

    private static final String HOST = ManifestReader.getMetaDataInApplication(MerchantApplication.getApplication(), "HOST");

    public static final String LOGIN = HOST + "Login/login";
    public static final String SEND_MESSAGE = HOST + "Login/getVerificationCode";
    public static final String VERIFICATION_CODE = HOST + "Login/verificationCode";
    public static final String RESET_PASSWORD = HOST + "Login/changePassword";
    public static final String REGISTER_MERCHAT = HOST + "Shop/Apply";
    public static final String HOME_DATA = HOST + "Home/getStat";
    public static final String BILL_HOUSTON = HOST + "Account/orderPayment";
    public static final String BILL_GET_ALL_STORE = HOST + "Account/getStoreList";
    public static final String BILL_GET_MEMBER_BILL = HOST + "Account/storePayment";
    public static final String GET_MEMBER_MANAGER_LIST = HOST + "ApiHomeindex/MemberManager";
    public static final String GET_MEMBER_DATA = HOST + "ApiHomeindex/DataStatistics";
    public static final String GET_BANK_CARD = HOST + "Account/getShopAccountWithdraw";
    public static final String GET_MEMBER_BALANCE = HOST + "ApiCashout/GetAmount";
    public static final String WITH_DRAW_MONEY = HOST + "Account/applyShopAccountWithdraw";
    public static final String GET_WITH_DRAW_MONEY_RECORD = HOST + "Account/shopAccountWithdrawList";
    public static final String USER_DATA = HOST + "Personal/personal";
    public static final String GET_MERCHANT_LIST = HOST + "Stores/stores";
    public static final String GET_EMPLOYEE_LIST = HOST + "ApiSystemUser/EmployeeManager";
    public static final String GET_MERCHANT_QR_CODE = HOST + "Code/Code";
    public static final String GET_BIND_INFO = HOST + "Authentication/authentication";
    public static final String GET_INFORMATION = HOST + "ApiSystemUser/BasicInfo";
    public static final String SET_VOICE_INDEX = HOST + "Voice/voice";
    public static final String SET_VOICE = HOST + "Voice/voices";
    public static final String SET_VOICE_SOUND = HOST + "Voice/sounds";
    public static final String SET_VOICE_PUSH = HOST + "Voice/letter";
    public static final String GET_MERCHANT_VOICE_INDEX = HOST + "ApiSystemUser/VoiceStoreSet";
    public static final String SET_MERCHANT_VOICE = HOST + "ApiSystemUser/VoiceStoreUpdate";
    public static final String GET_JPUSH_TAG_ALIAS = HOST + "ApiAccount/GetUserInfo";
    public static final String UPLOAD_IMG_FILES = HOST + "Upload/avatorUpload";
    public static final String UPLOAD_IMG_PICS = HOST + "Upload/Uploadpics";
    public static final String PUSH_FEED_BACK = HOST + "SystemUser/Feedback";
    public static final String VERIFICATION_INDEX = HOST + "Order/qrCode";
    public static final String VERIFICATION_SET = HOST + "Order/cancel";
    public static final String GET_SERVICE_PHONE = HOST + "Login/GetPhone";
    public static final String CHANGE_PROFILE_PIC = HOST + "Login/modifyHeadImg";
    public static final String CHANGE_PASSWORD = HOST + "Login/changePwd";
    public static final String BILL_GET_RECONCILIATION = HOST + "Apibill/Reconciliation";
    public static final String BILL_GET_BILL_LIST = HOST + "Apibill/BillList";
    public static final String GET_ORDER_DATA = HOST + "ApiShopOrder/Index";
    public static final String GET_ORDER_LIST = HOST + "Order/OrderList";
    public static final String GET_DELIVER_ORDER_LIST = HOST + "ApiShopOrder/DeliverGoodsList";
    public static final String GET_REFUND_ORDER_LIST = HOST + "Order/getCustomerServiceList";
    public static final String GET_ORDER_DETAIL = HOST + "Order/OrderDetail";
    public static final String GET_DELIVER_ORDER_DETAIL = HOST + "ApiShopOrder/DeliverGoodsDetail";
    public static final String GET_REFUND_ORDER_DETAIL = HOST + "Order/orderCustomerDetail";
    public static final String GET_EXPRESS_COMPANY_LIST = HOST + "Order/getExpressCompanyList";
    public static final String DELIVER_GOODS = HOST + "Order/orderDelivery";
    public static final String ALLOW_REFUND = HOST + "ShopOrder/RefundSuccess";
    public static final String DISALLOW_REFUND = HOST + "ShopOrder/RefundFail";
    public static final String GET_ARTICLE_LIST = HOST + "Home/getMessage";//通知
    public static final String GET_CONSULT_LIST = HOST + "Home/getConsult";//咨询
    public static final String GET_HOUSTON_DETAIL = HOST + "Account/getOrderPayInfo";
    public static final String GET_MEMBER_DETAIL = HOST + "Apibill/BillMemberDetail";
    public static final String GET_CONTRACT_INFORMATION = HOST + "Commission/commission";
    public static final String GET_MERCHANT_INFO = HOST + "Merchant/merchant";
    public static final String GET_MERCHANT_LIST_BY_AGENT = HOST + "Business/business";
    public static final String GET_MERCHANT_DETAIL = HOST + "Data/datas";
    public static final String GET_MERCHANT_CLASSIFY = HOST + "Business/cate";
    public static final String QUERY_GOODS_SALE_RANK = HOST + "Home/getGoodsRealSalesRank";
    public static final String QUERY_BANK_ACCOUNT_LIST = HOST + "Account/banklist";
    public static final String CHANGE_DEFAULT_BANK = HOST + "Account/bankDefault";
    public static final String QUERY_GOODS_LIST = HOST + "Commodity/commodity";
    public static final String CHANGE_GOODS_STATUS_PUT_AWAY = HOST + "Goods/stata";
    public static final String CHANGE_GOODS_STATUS_SOLD_DOWN = HOST + "Goods/downs";
    public static final String CHANGE_GOODS_STATUS_APPLY_PASS = HOST + "Goods/statagent";
    public static final String GET_GOODS_DETAIL = HOST + "Goods/goods";
    public static final String MERCHANT_REPORT_FORMS_MONEY = HOST + "Data/moneyCount";
    public static final String MERCHANT_REPORT_FORMS_GOODS = HOST + "Data/goods";
    public static final String MERCHANT_REPORT_FORMS_USER = HOST + "Data/users";
    public static final String MERCHANT_REPORT_FORMS_ORDER = HOST + "Data/orders";
    public static final String QUERY_RECHARGE_LIST = HOST + "Balance/recharge";
    public static final String AGREE_ORDER_REFUND = HOST + "Order/orderGoodsRefundAgree";
    public static final String DISAGREE_ORDER_REFUND = HOST + "Order/orderCustomerRefuseOnce";
    public static final String MERCHANT_INFORMATION = HOST + "Information/information";
    public static final String ORDER_REPORT_DATA = HOST + "Order/orderNumCurve";
    public static final String SALE_REPORT_DATA = HOST + "Order/orderMoneyCurve";
    public static final String GET_ORDER_COUNT = HOST + "Home/getNewOrderCount";
    public static final String GET_ORDER_NOTICE = HOST + "Order/getNotice";
    public static final String GET_MEMBERSHIP_LIST = HOST + "Member/getList";
    public static final String GET_MEMBERSHIP_LEVEL = HOST + "Member/getLevelList";
    public static final String LOGOUT = HOST + "Login/Logout";

}
