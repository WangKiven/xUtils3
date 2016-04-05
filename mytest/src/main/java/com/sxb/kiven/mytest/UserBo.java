package com.sxb.kiven.mytest;

/**
 * Created by kiven on 16/3/31.
 */
public class UserBo {

    /**
     *
     */
    private static final long serialVersionUID = 3057281663334288623L;

    public UserBo(  ) { }

    private int id;

    private String token;
    private String privateKey;// 私钥

    private String userAccount;// 用户账号（注册时填的电话号码)
    private int userID; // 用户ID
    private String userName; // 用户名
    private String userHead;// 用户头像地址
    private String identityCard;// 用户身份证号码

    private String customerServiecePhone;// 用户对应分组下的客服电话

    private String createDate;// 创建时间
    private int credibility;// Int 类型，信誉度 1-100
    private int score;// 用户积分

    private boolean signin;// 今天是否签到
    private int checkStatus;// 审核状态：0: 停用 1：启用    2：待审核 3：待完善
    private int authStatus;// 认证状态 0-没有认证 1-认证成功 2-认证失败 3-认证中

    private int unreadAnnouCount;// 未读公告数量
    private int unreadMessageCount;// 未读消息数量(认证成功，审核失败。。。。)

    private String contactPhone;// 联系电话
    private String officePhone;// 办公电话
    private String mainBrand;// 主营品牌
    private String businessNature;// 商家性质 4S店；直营商家；综合商家；资源商家；厂家
    private String businessName;// 商家名称
    private String address;// 所在地区
    private boolean isVip; // 是否是Vip
    private boolean userIsBigWallet;			//用户的钱包是否是很有钱


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard( String identityCard ) {
        this.identityCard = identityCard;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress( String address ) {
        this.address = address;
    }

    public String getMainBrand() {
        return mainBrand;
    }

    public void setMainBrand( String mainBrand ) {
        this.mainBrand = mainBrand;
    }

    public int getScore() {
        if (score < 0) {
            return 0;
        }
        return score;
    }

    public void setScore( int score ) {
        this.score = score;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate( String createDate ) {
        this.createDate = createDate;
    }


    public boolean isSignin() {
        return signin;
    }

    public void setSignin( boolean signin ) {
        this.signin = signin;
    }
    public int getCredibility() {
        return credibility;
    }

    public void setCredibility( int credibility ) {
        this.credibility = credibility;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName == null? "": userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }

    public String getCustomerServiecePhone() {
        return customerServiecePhone;
    }

    public void setCustomerServiecePhone(String customerServiecePhone) {
        this.customerServiecePhone = customerServiecePhone;
    }

    public int getCheckStatus() {
        if (checkStatus < 0 || checkStatus > 3) {
            checkStatus = 0;
        }
        return checkStatus;
    }

    public void setCheckStatus(int checkStatus) {
        this.checkStatus = checkStatus;
    }

    public int getAuthStatus() {
        if (authStatus < 0 || authStatus > 3) {
            authStatus = 0;
        }
        return authStatus;
    }

    public void setAuthStatus(int authStatus) {
        this.authStatus = authStatus;
    }

    public int getUnreadAnnouCount() {
        return unreadAnnouCount;
    }

    public void setUnreadAnnouCount(int unreadAnnouCount) {
        this.unreadAnnouCount = unreadAnnouCount;
    }

    public int getUnreadMessageCount() {
        return unreadMessageCount;
    }

    public void setUnreadMessageCount(int unreadMessageCount) {
        this.unreadMessageCount = unreadMessageCount;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getBusinessNature() {
        return businessNature;
    }

    public void setBusinessNature(String businessNature) {
        this.businessNature = businessNature;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public boolean isVip() {
        return isVip;
    }

    public void setVip(boolean isVip) {
        this.isVip = isVip;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public boolean isUserIsBigWallet() {
        return userIsBigWallet;
    }

    public void setUserIsBigWallet(boolean userIsBigWallet) {
        this.userIsBigWallet = userIsBigWallet;
    }

    /**
     * 是否被启用
     * @return
     */
    public boolean hasChecked(){
        return checkStatus == 1;
    }
    /**
     * 是否被认证
     * @return
     */
    public boolean hasAuth(){
        return authStatus == 1;
    }
    /**
     * 是否是4s
     * @return
     */
    public boolean is4s(){
        return businessNature != null && businessNature.contains("4");
    }
}