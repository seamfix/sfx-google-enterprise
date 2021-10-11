import com.google.api.services.androidmanagement.v1.AndroidManagement;
import com.google.api.services.androidmanagement.v1.model.EnrollmentToken;

import java.io.IOException;

public class CreateEnrollmentToken {

    private AndroidManagement androidManagement;

    public CreateEnrollmentToken(AndroidManagement androidManagement) {
        this.androidManagement = androidManagement;
    }

    /** Creates an enrollment token. */
    public String createEnrollmentToken(OutputType outputType, String enterpriseName, String policyId)
            throws IOException {
        System.out.println("Creating enrollment token...");
        EnrollmentToken token = new EnrollmentToken().setPolicyName(policyId).setDuration("86400s");
        String enrollmentTokenValue;

        if(outputType == OutputType.URL_STRING) {
            enrollmentTokenValue = androidManagement
                    .enterprises()
                    .enrollmentTokens()
                    .create(enterpriseName, token)
                    .execute()
                    .getValue();
        } else {
            enrollmentTokenValue = androidManagement
                    .enterprises()
                    .enrollmentTokens()
                    .create(enterpriseName, token)
                    .execute()
                    .getQrCode();
        }

        System.out.println("Enrollment token created successfully:\n"+ enrollmentTokenValue);
        return enrollmentTokenValue;
    }


    enum  OutputType {
        QR_CODE, URL_STRING;
    }
}
