package com.shoot.common.safe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;


public class XSignatureUtil {

    final String TAG = "SignatureUtil";

    @SuppressLint("PackageManagerGetSignatures")
    public static String getCertificateSHA1Fingerprint(Context context) {
        PackageManager pm = context.getPackageManager();

        String packageName = context.getPackageName();

        int flags = PackageManager.GET_SIGNATURES;

        PackageInfo packageInfo = null;

        try {
            packageInfo = pm.getPackageInfo(packageName, flags);
            if (packageInfo == null){
                return "";
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null){
            return "";
        }
        Signature[] signatures = packageInfo.signatures;
        byte[] cert = signatures[0].toByteArray();

        InputStream input = new ByteArrayInputStream(cert);

        CertificateFactory cf = null;

        try {
            cf = CertificateFactory.getInstance("X509");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //X509 证书，X.509 是一种非常通用的证书格式
        X509Certificate c = null;

        try {
            if (cf != null){
                c = (X509Certificate) cf.generateCertificate(input);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String hexString = null;

        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            if (c == null){
                return "";
            }
            byte[] publicKey = md.digest(c.getEncoded());

            hexString = byte2HexFormatted(publicKey);

        } catch (NoSuchAlgorithmException | CertificateEncodingException e1) {
            e1.printStackTrace();
        }
        return hexString;
    }

    /**
     * 这里是将获取到得编码进行16 进制转换
     *
     * @param arr x
     * @return ""
     */
    private static String byte2HexFormatted(byte[] arr) {
        StringBuilder str = new StringBuilder(arr.length * 2);

        for (int i = 0; i < arr.length; i++) {
            String h = Integer.toHexString(arr[i]);
            int l = h.length();
            if (l == 1)
                h = "0" + h;
            if (l > 2)
                h = h.substring(l - 2, l);
            str.append(h.toUpperCase());
            if (i < (arr.length - 1))
                str.append(':');
        }
        return str.toString();
    }



}
