import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.androidmanagement.v1.AndroidManagement;
import com.google.api.services.androidmanagement.v1.model.Policy;
import createenterprise.CreateEnterprise;
import createenterprise.util.Result;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

public class SfxAndroidEnterprise {

    /** The OAuth scope for the Android Management API. */
    private static final String OAUTH_SCOPE =
            "https://www.googleapis.com/auth/androidmanagement";

    private static AndroidManagement androidManagement;

    /** Builds an Android Management API client. */
    private static AndroidManagement getAndroidManagementClient(String serviceAccountCredentialFilePath , String appName)
            throws IOException, GeneralSecurityException {
        try (FileInputStream input = new FileInputStream(serviceAccountCredentialFilePath)) {
            GoogleCredential credential = GoogleCredential.fromStream(input).createScoped(Collections.singleton(OAUTH_SCOPE));
            return new AndroidManagement.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    JacksonFactory.getDefaultInstance(),
                    credential
            ).setApplicationName(appName).build();
        }
    }

    public static void init(String serviceAccountCredentialFilePath,
                            String appName) throws IOException, GeneralSecurityException {
        androidManagement = getAndroidManagementClient(serviceAccountCredentialFilePath, appName);
    }

    /** Creates a new enterprise. Returns the enterprise name.
     * @param  googleCloudConsoleProjectID: your Google cloud console project ID
     * @param enterpriseToken: the token value returned after the user has completed enterprise creation with Google. If
     * you set this value to null, we will return a redirect result which means that the user should be redirected to
     * Google enterprise creation page to create his enterprise.
     * */
    public Result createEnterprise(String googleCloudConsoleProjectID,
                                   String callBackUrl,
                                   String enterpriseToken) throws IOException {

        CreateEnterprise createEnterprise = new CreateEnterprise(androidManagement);
        return createEnterprise.createEnterprise(googleCloudConsoleProjectID, callBackUrl, enterpriseToken);
    }

    public static void createPolicy(String enterpriseName, String policyId, Policy policy) throws IOException {
        CreatePolicy createPolicy = new CreatePolicy(androidManagement);
        createPolicy.setPolicy(enterpriseName, policyId, policy);
    }

    /***
     * Creates an enrollment token.
     * @param outputType: Determines the kind of output result. QR_CODE for QR code and URL_STRING for a url.
     * @param enterpriseName: The name of your enterprise.
     * @param policyId: The ID of the policy you created.
     * @return either a QR code payload or a URL enrollment string.
     * @throws IOException
     */
    public static String createEnrollmentToken(CreateEnrollmentToken.OutputType outputType,
                                               String enterpriseName,
                                               String policyId) throws IOException {

        CreateEnrollmentToken createEnrollmentToken = new CreateEnrollmentToken(androidManagement);
        return createEnrollmentToken.createEnrollmentToken(outputType, enterpriseName, policyId);
    }

    public AndroidManagement getAndroidManagement() {
        return androidManagement;
    }

    public void setAndroidManagement(AndroidManagement androidMngt) {
        androidManagement = androidMngt;
    }
}
