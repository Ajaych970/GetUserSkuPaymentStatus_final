package Http.RequestPOJO;

public class RequestPOJO {


    private boolean requestContext;

    private String action;

    private String appId;

    private String gocId;

    private String PGTransactionId;

    private String transactionId;


    public RequestPOJO() {
    }

    public RequestPOJO(boolean requestContext, String action, String appId, String gocId, String PGTransactionId, String transactionId) {
        this.requestContext = requestContext;
        this.action = action;
        this.appId = appId;
        this.gocId = gocId;
        this.PGTransactionId = PGTransactionId;
        this.transactionId = transactionId;
    }

    public boolean isRequestContext() {
        return requestContext;
    }

    public void setRequestContext(boolean requestContext) {
        this.requestContext = requestContext;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getGocId() {
        return gocId;
    }

    public void setGocId(String gocId) {
        this.gocId = gocId;
    }

    public String getPGTransactionId() {
        return PGTransactionId;
    }

    public void setPGTransactionId(String PGTransactionId) {
        this.PGTransactionId = PGTransactionId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
