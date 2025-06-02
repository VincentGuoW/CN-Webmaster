public class Test {
    public static void main(String[] args) throws Exception {
        String containerNumber = "MEDU7609650";
        String token = OAuthTokenFetcher.getAccessToken();
        String result = EstimatedTime.getETA(token, containerNumber);

        System.out.println(result);
    }
}
