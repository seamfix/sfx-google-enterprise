import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.androidmanagement.v1.AndroidManagement;
import com.google.api.services.androidmanagement.v1.model.Policy;
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

    public static void init(String serviceAccountCredentialFilePath, String appName) throws IOException, GeneralSecurityException {
        androidManagement = getAndroidManagementClient(serviceAccountCredentialFilePath, appName);
    }

    public static void createEnterprise(String projectId) throws IOException {
        CreateEnterprise createEnterprise = new CreateEnterprise();
        createEnterprise.createEnterprise(androidManagement, projectId);
    }

    public static void createPolicy(String enterpriseName, String policyId, Policy policy) throws IOException {
        CreatePolicy createPolicy = new CreatePolicy();
        createPolicy.setPolicy(androidManagement, enterpriseName, policyId, policy);
    }

    public static void createEnrollmentToken(CreateEnrollmentToken.OutputType outputType, String enterpriseName, String policyId) throws IOException {
        CreateEnrollmentToken createEnrollmentToken = new CreateEnrollmentToken();
        createEnrollmentToken.createEnrollmentToken(androidManagement, outputType, enterpriseName, policyId);
    }

    public AndroidManagement getAndroidManagement() {
        return androidManagement;
    }

    public void setAndroidManagement(AndroidManagement androidMngt) {
        androidManagement = androidMngt;
    }
}
