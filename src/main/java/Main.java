import com.google.api.services.androidmanagement.v1.model.ApplicationPolicy;
import com.google.api.services.androidmanagement.v1.model.PersistentPreferredActivity;
import com.google.api.services.androidmanagement.v1.model.Policy;
import com.google.api.services.androidmanagement.v1.model.SignupUrl;
import createenterprise.util.Result;
import createenterprise.util.Type;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String []args) throws IOException, GeneralSecurityException {

        //STEP 1
        SfxAndroidEnterprise.init("/Users/jeffemuveyan/Downloads/veyanjeffmdmproject-ac800f100ad3.json", "cloudApp");

        //STEP 2
        Result result = SfxAndroidEnterprise.createEnterprise("veyanjeffmdmproject", "https://www.wwe.com");
        //Redirect the user to this url to begin the signup process:
        System.out.println("*******: SignupUrl name -> "+result.getSignUpUrl().getName());
        System.out.println("*******: SignupUrl url -> "+result.getSignUpUrl().getUrl());

        //STEP 3
        SignupUrl signupUrl = new SignupUrl();
        signupUrl.setName("signupUrls/C749013e4317aa4c0");
        signupUrl.setUrl("https://play.google.com/work/adminsignup?token=SAJmqckwcEKVtaCQWaXeTMxB15k-_t1KbnQ_rOzdjJG5YGXn_wJWtLBKdn6r49ahuotTM0WISOII49mR-NH9vYEODTjXB43CFu7qlce83PY6_v1fqVlDZ_s_gGsbAuWJgJM5hsENYRkt-292-Thu8-k7V6pdmTzrNZOYcwFOhscoVxtg3YYmMk7Fapd9u12E5IP1cXpaGjD2f");
        Result r = SfxAndroidEnterprise.completeEnterpriseCreation("veyanjeffmdmproject",signupUrl, "EAJmqckyDdrTspj4mFSSoXZY82fntNfSK-6xqwIpxNLVtH-wQf1kdPVjRQw4rsPlLlFD8yA48s4kNuyj1p3s3us_JuYKtwGP_5OnvnSVqPzxuBOCOmBiKXzo");
        System.out.println("*******: Enterprise created with name -> "+r.getEnterpriseName());

        //STEP 4
        SfxAndroidEnterprise.createPolicy("enterprises/LC02v8m2ug", "ID", getCosuPolicy());
        String url = SfxAndroidEnterprise.createEnrollmentToken(CreateEnrollmentToken.OutputType.URL_STRING, "enterprises/LC02v8m2ug", "ID");
        System.out.println("*******: enrollment token: "+url);
    }


    private static Policy getCosuPolicy() {
        List<String> categories = new ArrayList<>();
        categories.add("android.intent.category.HOME");
        categories.add("android.intent.category.DEFAULT");

        return new Policy()
                .setApplications(
                        Collections.singletonList(
                                new ApplicationPolicy()
                                        .setPackageName("com.seamfix.bioregistra")
                                        .setInstallType("FORCE_INSTALLED")
                                        .setDefaultPermissionPolicy("GRANT")
                                        .setLockTaskAllowed(true)))
                .setPersistentPreferredActivities(
                        Collections.singletonList(
                                new PersistentPreferredActivity()
                                        .setReceiverActivity("com.seamfix.bioregistra")
                                        .setActions(
                                                Collections.singletonList("android.intent.action.MAIN"))
                                        .setCategories(categories)))
                .setKeyguardDisabled(true)
                .setStatusBarDisabled(true);
    }
}
