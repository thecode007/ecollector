package com.thecode007.ecollecter.network;

import android.content.Context;
import com.google.gson.Gson;
import com.thecode007.ecollecter.prefs.PreferencesHelper;


/**
 * Created by Assad on 07/07/17.
 */

public class AppDataManager implements DataManager {

    private  ApiHelper mApiHelper;
    private final PreferencesHelper mPreferencesHelper;

    public void setmApiHelper(ApiHelper mApiHelper) {
        this.mApiHelper = mApiHelper;
    }


    public AppDataManager(Context context,
                          PreferencesHelper preferencesHelper, ApiHelper apiHelper,
                          Gson gson) {
        mPreferencesHelper = preferencesHelper;
        mApiHelper = apiHelper;
    }

    @Override
    public ApiHeader getApiHeader() {
        return null;
    }

    @Override
    public void setApiHeader(ApiHeader apiHeader) {

    }

    @Override
    public void setAppVersion(String version) {

    }


//
//    public  <T> Single<T> authenticatedSingleApi(Single<T> single) {
//        return single.flatMap(response -> {
//            if (((BaseResponse) response).getStatus() == 111) {
//                RefreshTokenReq refreshTokenReq = new RefreshTokenReq();
//                refreshTokenReq.setRefreshToken(mPreferencesHelper.getSignRsp().getRefreshToken());
//                return refreshToken(refreshTokenReq).flatMap(refreshTokenResponse -> {
//                    mPreferencesHelper.setSignRsp(refreshTokenResponse.getData());
//                    return single;
//                });
//            } else {
//                return Single.just(response);
//            }
//        }).onErrorResumeNext(refreshTokenAndRetryObser(single));
//    }
//
//
//
//
//
//    private <T> Function<Throwable, ? extends Single<? extends T>> refreshTokenAndRetryObser(final Single<T> toBeResumed) {
//        return (Function<Throwable, Single<? extends T>>) throwable -> {
//            if (ExceptionStatusCode.isHttp401Error(throwable)) {
//                RefreshTokenReq refreshTokenReq = new RefreshTokenReq();
//                refreshTokenReq.setRefreshToken(mPreferencesHelper.getSignRsp().getRefreshToken());
//                return refreshToken(refreshTokenReq).flatMap(refreshTokenResponse -> {
//                    if (refreshTokenResponse.getStatus() == 1) {
//                        mPreferencesHelper.setSignRsp(refreshTokenResponse.getData());
//                        return toBeResumed;
//                    } else if (refreshTokenResponse.getStatus() == 0) {
//                        if (null != globalInterface)
//                            globalInterface.onExit();
//                        return Single.error(throwable);
//                    } else return Single.error(throwable);
//                });
//            }
//            return Single.error(throwable);
//        };
//    }
//
//    @Override
//    public ApiHeader getApiHeader() {
//        return mApiHelper.getApiHeader();
//    }
//
//    @Override
//    public void setApiHeader(ApiHeader apiHeader) {
//        mApiHelper.setApiHeader(apiHeader);
//    }
//
//    @Override
//    public Single<BaseResponse<SignInRsp>> refreshToken(RefreshTokenReq refreshTokenReq) {
//        return mApiHelper.refreshToken(refreshTokenReq);
//    }
//
//    @Override
//    public Single<BaseResponse<Boolean>> setLanguage(String language) {
//        return authenticatedSingleApi(mApiHelper.setLanguage(language));
//    }
//
//
//    @Override
//    public Single<BaseResponse<Boolean>> forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
//        Single<BaseResponse<Boolean>> single = mApiHelper.forgotPassword(forgotPasswordRequest);
//        return authenticatedSingleApi(single);
//    }
//
//    @Override
//    public Single<BaseResponse<Boolean>> changePassword(ChangePasswordRequest changePasswordRequest) {
//        return authenticatedSingleApi(mApiHelper.changePassword(changePasswordRequest));
//    }
//
//    @Override
//    public Single<BaseResponse<Boolean>> signUp(UpdateProfileRequest profileFields) {
//        return authenticatedSingleApi(mApiHelper.signUp(profileFields));
//    }
//
//    @Override
//    public Single<BaseResponse<Boolean>> signUpV2(UpdateProfileRequestV2 profileFields) {
//        return authenticatedSingleApi(mApiHelper.signUpV2(profileFields));
//    }
//
//    @Override
//    public Single<BaseResponse<SignInRsp>> signIn(SignInReq signInReq) {
//        return authenticatedSingleApi(mApiHelper.signIn(signInReq));
//    }
//
//    @Override
//    public Single<BaseResponse<Boolean>> signOut(EmptyObject emptyObject) {
//        return authenticatedSingleApi(mApiHelper.signOut(emptyObject));
//    }
//
//    @Override
//    public Single<BaseResponse<List<ProductCategory>>> getAllTypes() {
//        return authenticatedSingleApi(mApiHelper.getAllTypes());
//    }
//
//    @Override
//    public Single<BaseResponse<List<SubCategory>>> getAllCategories(TypeRequest typeRequest) {
//        return authenticatedSingleApi(mApiHelper.getAllCategories(typeRequest));
//    }
//
//    @Override
//    public Single<BaseResponse<GetAllBrandRsp>> getAllBrands(GetAllBrandReq search) {
//        return authenticatedSingleApi(mApiHelper.getAllBrands(search));
//    }
//
//    @Override
//    public Single<BaseResponse<DailyMessageRsp>> getDailyMessage() throws JSONException {
//        return authenticatedSingleApi(mApiHelper.getDailyMessage());
//    }
//
//    @Override
//    public Single<BaseResponse<AllMessagesRsp>> getAllMessages(AllMessagesReq allMessagesReq) {
//        return authenticatedSingleApi(mApiHelper.getAllMessages(allMessagesReq));
//    }
//
//    @Override
//    public Single<BaseResponse<ProfileData>> getProfile(ProfileRequest profileRequest) {
//        return authenticatedSingleApi(mApiHelper.getProfile(profileRequest));
//    }
//
//    @Override
//    public Single<BaseResponse<Boolean>> updateProfile(UpdateProfileRequest profileFields) {
//        return authenticatedSingleApi(mApiHelper.updateProfile(profileFields));
//    }
//
//    @Override
//    public Single<BaseResponse<AdResponse>> getAdsByTag(CategoryTag categoryTag) {
//        return authenticatedSingleApi(mApiHelper.getAdsByTag(categoryTag));
//    }
//
//    @Override
//    public Single<ForecastData> getForecast(Double longitude, Double altitude) {
//        return mApiHelper.getForecast(longitude, altitude);
//    }
//
//    @Override
//    public Single<BaseResponse<GetAllVideosRsp>> getAllMainVideos(GetAllVideos getAllVideos) {
//        return authenticatedSingleApi(mApiHelper.getAllMainVideos(getAllVideos));
//    }
//
//
//
//    @Override
//    public Single<BaseResponse<ArrayList<FaqRsp>>> getContent(FaqReq faqReq) {
//        return authenticatedSingleApi(mApiHelper.getContent(faqReq));
//    }
//
//    @Override
//    public Single<BaseResponse<SearchRsp>> getCategoryListingDetailItem(SearchReq searchReq) {
//        return authenticatedSingleApi(mApiHelper.getCategoryListingDetailItem(searchReq));
//    }
//
//    @Override
//    public Single<BaseResponse<String>> submitSurvey(SurveySubmit surveySubmit) {
//        return authenticatedSingleApi(mApiHelper.submitSurvey(surveySubmit));
//    }
//
//    @Override
//    public Single<BaseResponse<ArrayList<String>>> submitTempSurvey(SurveySubmit surveySubmit) {
//        return authenticatedSingleApi(mApiHelper.submitTempSurvey(surveySubmit));
//    }
//
//    @Override
//    public Single<BaseResponse<VoucherValidate>> validateVoucher(VoucherValidateRequest voucherValidateRequest) {
//        return authenticatedSingleApi(mApiHelper.validateVoucher(voucherValidateRequest));
//    }
//
//    @Override
//    public Single<BaseResponse<List<Survey>>> getSurveys(TestRequest testRequest) {
//        return authenticatedSingleApi(mApiHelper.getSurveys(testRequest));
//    }
//
//    @Override
//    public Single<BaseResponse<List<FilterRsp>>> getFilters() {
//        return mApiHelper.getFilters();
//    }
//
//    @Override
//    public Single<BaseResponse<FaceData>> scanFace(ScanRequest scanRequest, UploadProgressListener uploadProgressListener) {
//        return authenticatedSingleApi(mApiHelper.scanFace(scanRequest, uploadProgressListener));
//    }
//
//    @Override
//    public Single<BaseResponse<SignInRsp>> anonymousLogIn() {
//        return mApiHelper.anonymousLogIn();
//    }
//
//    @Override
//    public Single<BaseResponse<String>> anonymous() {
//        return mApiHelper.anonymous();
//    }
//
//    @Override
//    public Single<BaseResponse<GetRecommendedRsp>> getRecommended(GetRecommended getRecommended) {
//        return authenticatedSingleApi(mApiHelper.getRecommended(getRecommended));
//    }
//
//    @Override
//    public Single<BaseResponse<PackagesRsp>> getRecommendedPackages(GetRecommended getRecommended) {
//        return authenticatedSingleApi(mApiHelper.getRecommendedPackages(getRecommended));
//    }
//
//    @Override
//    public Single<BaseResponse<ShippingAddressRsp>> getMyAddresses() {
//        return authenticatedSingleApi(mApiHelper.getMyAddresses());
//    }
//
//    @Override
//    public Single<BaseResponse<ShippingAddress>> getAddresseByGuid(GetAddress getAddress) {
//        return authenticatedSingleApi(mApiHelper.getAddresseByGuid(getAddress));
//    }
//
//    @Override
//    public Single<BaseResponse<String>> deleteAddress(GetAddress getAddress) {
//        return authenticatedSingleApi(mApiHelper.deleteAddress(getAddress));
//    }
//
//    @Override
//    public Single<BaseResponse<ArrayList<AreasRsp>>> getAreas() {
//        return mApiHelper.getAreas();
//    }
//
//    @Override
//    public Single<BaseResponse<String>> addNewAddress(AddAddress addAddress) {
//        return authenticatedSingleApi(mApiHelper.addNewAddress(addAddress));
//    }
//
//    @Override
//    public Single<BaseResponse<String>> setAddressDefault(GetAddress addAddress) {
//        return authenticatedSingleApi(mApiHelper.setAddressDefault(addAddress));
//    }
//
//    @Override
//    public Single<BaseResponse<ArrayList<VideoCategory>>> getVideoCategories() {
//        return authenticatedSingleApi(mApiHelper.getVideoCategories());
//    }
//
//    @Override
//    public Single<BaseResponse<GetAllVideosRsp>> searchVideos(SearchReq searchReq) {
//        return authenticatedSingleApi(mApiHelper.searchVideos(searchReq));
//    }
//
//    @Override
//    public Single<BaseResponse<GetItemDetailsRsp>> getItemDetails(GetItemDetailsReq getItemDetailsReq) {
//        return authenticatedSingleApi(mApiHelper.getItemDetails(getItemDetailsReq));
//    }
//
//    @Override
//    public Single<BaseResponse<CustomerReviews>> getAllComments(GetAllCommentsReq getAllCommentsReq) {
//        return authenticatedSingleApi(mApiHelper.getAllComments(getAllCommentsReq));
//    }
//
//    @Override
//    public Single<BaseResponse<CheckBodyInfoRsp>> haseFaceScan() {
//        return authenticatedSingleApi(mApiHelper.haseFaceScan());
//    }
//
//    @Override
//    public Single<BaseResponse<Boolean>> addToWishList(AddToWishList addToWishList) {
//        return authenticatedSingleApi(mApiHelper.addToWishList(addToWishList)).map(response -> {
//            if (response.getStatus() == Keys.HTTP_STATUS_SUCCESS) {
//                if (addToWishList.isLike())
//                    addWishListCounter();
//                else
//                    removeWishListCounter();
//            }
//            return response;
//        });
//    }
//
//    @Override
//    public Single<BaseResponse<GetPackageDetailsRsp>> getPacakgeDetails(GetPackageDetails getPackageDetails) {
//        return authenticatedSingleApi(mApiHelper.getPacakgeDetails(getPackageDetails));
//    }
//
//    @Override
//    public Single<BaseResponse<WishListResponse>> getWishList(WishRequest wishRequest) {
//        return authenticatedSingleApi(mApiHelper.getWishList(wishRequest));
//    }
//
//    @Override
//    public Single<BaseResponse<String>> addToBag(AddToBagBody addToBagBody) {
//        return authenticatedSingleApi(mApiHelper.addToBag(addToBagBody).map(stringBaseResponse ->{
//            mPreferencesHelper.setBagNumbers(Integer.parseInt(stringBaseResponse.getData()));
//            return stringBaseResponse;
//        }));
//    }
//
//    @Override
//    public Single<BaseResponse<GetAllBagRsp>> getAllBag(AddToBagBody addToBagBody) {
//            return authenticatedSingleApi(mApiHelper.getAllBag(addToBagBody).map(
//                response -> {
//                    mPreferencesHelper.setBagNumbers(response.getData().getItems().size());
//                return response;
//            }));
//    }
//
//    @Override
//    public Single<BaseResponse<String>> removeBagItem(RemoveBagItem removeBagItem) {
//        return authenticatedSingleApi(mApiHelper.removeBagItem(removeBagItem));
//    }
//
//    @Override
//    public Single<BaseResponse<TimeSlots>> getSlotTime(WishRequest userGuid) {
//        return authenticatedSingleApi(mApiHelper.getSlotTime(userGuid));
//    }
//
//    @Override
//    public Single<BaseResponse<ConfirmOrderRsp>> confirmOrder(ConfirmOrderReq confirmOrderReq) {
//        return authenticatedSingleApi(mApiHelper.confirmOrder(confirmOrderReq));
//    }
//
//    @Override
//    public Single<BaseResponse<GetDeliveryCostRsp>> getDeliveryCost(GetDeliveryCost getDeliveryCost) {
//        return authenticatedSingleApi(mApiHelper.getDeliveryCost(getDeliveryCost));
//    }
//
//    @Override
//    public Single<BaseResponse<CompleteOrderRsp>> completeOrder(CompleteOrderReq completeOrderReq) {
//        return authenticatedSingleApi(mApiHelper.completeOrder(completeOrderReq));
//    }
//
//    @Override
//    public Single<BaseResponse<MyOrderRsp>> getMyOrders(GetMyOrders getMyOrders) {
//        return authenticatedSingleApi(mApiHelper.getMyOrders(getMyOrders));
//    }
//
//    @Override
//    public Single<BaseResponse<TrackOrderRsp>> trackOrders(TrackOrderReq trackOrderRsp) {
//        return authenticatedSingleApi(mApiHelper.trackOrders(trackOrderRsp));
//    }
//
//    @Override
//    public Single<BaseResponse<EditOrderRsp>> editOrderItem(EditOrder editOrder) {
//        return authenticatedSingleApi(mApiHelper.editOrderItem(editOrder));
//    }
//
//    @Override
//    public Single<BaseResponse<PendingOrdersRsp>> getPendingOrders(GetPendingOrder getPendingOrder) {
//        return authenticatedSingleApi(mApiHelper.getPendingOrders(getPendingOrder));
//    }
//
//    @Override
//    public Single<BaseResponse<OrderDetailsRsp>> getOrderDetails(OrderDetailsReq getPendingOrder) {
//        return authenticatedSingleApi(mApiHelper.getOrderDetails(getPendingOrder));
//    }
//
//    @Override
//    public Single<BaseResponse<Boolean>> rateProduct(RateReq rateReq) {
//        return authenticatedSingleApi(mApiHelper.rateProduct(rateReq));
//    }
//
//    @Override
//    public Single<BaseResponse<ConfirmOrderRsp>> addSubOrder(AddSubOrder addSubOrder) {
//        return authenticatedSingleApi(mApiHelper.addSubOrder(addSubOrder));
//    }
//
//    @Override
//    public Single<BaseResponse<String>> moveToBag(MoveToBagReq addSubOrder) {
//        return authenticatedSingleApi(mApiHelper.moveToBag(addSubOrder));
//    }
//
//
//    @Override
//    public void setAppVersion(String version) {
//        mPreferencesHelper.setAppVersion(version);
//    }
//
//    @Override
//    public String getAppVersion() {
//        return mPreferencesHelper.getAppVersion();
//    }
//
//    @Override
//    public void setRegistrationStatus(int status) {
//        mPreferencesHelper.setRegistrationStatus(status);
//    }
//
//    @Override
//    public void setForecastData(ForecastData forecastData) {
//        mPreferencesHelper.setForecastData(forecastData);
//    }
//
//    @Override
//    public ForecastData getForecastData() {
//        return mPreferencesHelper.getForecastData();
//    }
//
//    @Override
//    public void setIsFirstTime(Boolean status) {
//        mPreferencesHelper.setIsFirstTime(status);
//    }
//
//    @Override
//    public void setUserName(String username) {
//        mPreferencesHelper.setUserName(username);
//    }
//
//    @Override
//    public String getUserName() {
//        return mPreferencesHelper.getUserName();
//    }
//
//    @Override
//    public Boolean isFirstTime() {
//        return mPreferencesHelper.isFirstTime();
//    }
//
//    @Override
//    public int getRegistrationStatus() {
//        return mPreferencesHelper.getRegistrationStatus();
//    }
//
//    @Override
//    public void setRegistrationType(int type) {
//        mPreferencesHelper.setRegistrationType(type);
//    }
//
//    @Override
//    public int getRegistrationType() {
//        return mPreferencesHelper.getRegistrationType();
//    }
//
//    @Override
//    public void setMyGUID(String guid) {
//        mPreferencesHelper.setMyGUID(guid);
//    }
//
//    @Override
//    public String getMyGuid() {
//        return mPreferencesHelper.getMyGuid();
//    }
//
//    @Override
//    public void setProfileGUID(String profileGUID) {
//        mPreferencesHelper.setProfileGUID(profileGUID);
//    }
//
//    @Override
//    public String getProfileGuid() {
//        return mPreferencesHelper.getProfileGuid();
//    }
//
//    @Override
//    public void setLatLon(ArrayList<Double> latlon) {
//        mPreferencesHelper.setLatLon(latlon);
//    }
//
//    @Override
//    public ArrayList<Double> getLatLon() {
//        return mPreferencesHelper.getLatLon();
//    }
//
//    @Override
//    public void setSignRsp(SignInRsp signrsp) {
//        mPreferencesHelper.setSignRsp(signrsp);
//        mPreferencesHelper.setMyGUID(signrsp.getGuid());
//        mApiHelper.getApiHeader().getProtectedApiHeader().setAccessToken(signrsp.getAccessToken());
//    }
//
//    @Override
//    public SignInRsp getSignRsp() {
//        return mPreferencesHelper.getSignRsp();
//    }
//
//    @Override
//    public void setDailyMessagePref(String message) {
//        mPreferencesHelper.setDailyMessagePref(message);
//    }
//
//    @Override
//    public String getDailyMessagePref() {
//        return mPreferencesHelper.getDailyMessagePref();
//    }
//
//    @Override
//    public void setDailyMessageTime(int day) {
//        mPreferencesHelper.setDailyMessageTime(day);
//    }
//
//    @Override
//    public void setRecommendedStringTitle(String title) {
//        mPreferencesHelper.setRecommendedStringTitle(title);
//    }
//
//    @Override
//    public String getRecommendedTitle() {
//        return mPreferencesHelper.getRecommendedTitle();
//    }
//
//    @Override
//    public int getDailyMessageTime() {
//        return mPreferencesHelper.getDailyMessageTime();
//    }
//
//    @Override
//    public void setIsLoggedIn(boolean isLoggedIn) {
//        mPreferencesHelper.setIsLoggedIn(isLoggedIn);
//    }
//
//    @Override
//    public void setCredentials(UpdateProfileRequestV2 credentials) {
//        mPreferencesHelper.setCredentials(credentials);
//    }
//
//    @Override
//    public void disableCredentials() {
//        mPreferencesHelper.disableCredentials();
//    }
//
//    @Override
//    public UpdateProfileRequestV2 getCredentials() {
//        return mPreferencesHelper.getCredentials();
//    }
//
//    @Override
//    public boolean isLoggedIn() {
//        return mPreferencesHelper.isLoggedIn();
//    }
//
//    @Override
//    public void setProfile(String profile) {
//        mPreferencesHelper.setProfile(profile);
//    }
//
//    @Override
//    public String getProfile() {
//        return mPreferencesHelper.getProfile();
//    }
//
//    @Override
//    public void setFaceAttribute(FaceAttribute faceAttribute) {
//        mPreferencesHelper.setFaceAttribute(faceAttribute);
//    }
//
//    @Override
//    public FaceAttribute getFaceAttribute() {
//        return mPreferencesHelper.getFaceAttribute();
//    }
//
//    @Override
//    public void setRelativeFaceAttribute(FaceAttribute faceAttribute) {
//        mPreferencesHelper.setRelativeFaceAttribute(faceAttribute);
//    }
//
//    @Override
//    public FaceAttribute getRelativeFaceAttribute() {
//        return mPreferencesHelper.getRelativeFaceAttribute();
//    }
//
//    @Override
//    public void setRelativeAnswers(ArrayList<String> answers) {
//         mPreferencesHelper.setRelativeAnswers(answers);
//    }
//
//    @Override
//    public void removeRelativeAnswers() {
//        mPreferencesHelper.removeRelativeAnswers();
//    }
//
//    @Override
//    public ArrayList<String> getRelativeAnswers() {
//        return mPreferencesHelper.getRelativeAnswers();
//    }
//
//    @Override
//    public void setFacebookLink(String facebookLink) {
//        mPreferencesHelper.setFacebookLink(facebookLink);
//    }
//
//    @Override
//    public String getFacebookLink() {
//        return mPreferencesHelper.getFacebookLink();
//    }
//
//    @Override
//    public void setInstaLink(String facebookLink) {
//        mPreferencesHelper.setInstaLink(facebookLink);
//    }
//
//    @Override
//    public String getInstaLin() {
//        return mPreferencesHelper.getInstaLin();
//    }
//
//    @Override
//    public boolean isSocialLogin() {
//        return mPreferencesHelper.isSocialLogin();
//    }
//
//    @Override
//    public void setIsSocialLogin(boolean isSocialLogin) {
//        mPreferencesHelper.setIsSocialLogin(isSocialLogin);
//    }
//
//    @Override
//    public void setWishListCounter(int counter) {
//        mPreferencesHelper.setWishListCounter(counter);
//    }
//
//    @Override
//    public void addWishListCounter() {
//        mPreferencesHelper.addWishListCounter();
//    }
//
//    @Override
//    public void removeWishListCounter() {
//        mPreferencesHelper.removeWishListCounter();
//    }
//
//    @Override
//    public int getWishListCounter() {
//        return mPreferencesHelper.getWishListCounter();
//    }
//
//    @Override
//    public void setBagNumbers(int bagNumbers) {
//        mPreferencesHelper.setBagNumbers(bagNumbers);
//    }
//
//    @Override
//    public int getBagNumbers() {
//        return mPreferencesHelper.getBagNumbers();
//    }
}







