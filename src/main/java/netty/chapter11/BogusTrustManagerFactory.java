package netty.chapter11;

import javax.net.ssl.ManagerFactoryParameters;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactorySpi;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.CertificateException;
import javax.security.cert.X509Certificate;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;

/**
 * Created with IntelliJ IDEA.
 * User: hucj
 * Date: 14-7-21
 * Time: 下午4:11
 * To change this template use File | Settings | File Templates.
 */
class BogusTrustManagerFactory extends TrustManagerFactorySpi {

    static final X509TrustManager X509 = new X509TrustManager() {
        public void checkClientTrusted(X509Certificate[] x509Certificates,
                                       String s) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] x509Certificates,
                                       String s) throws CertificateException {
        }

        @Override
        public void checkClientTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws java.security.cert.CertificateException {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void checkServerTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws java.security.cert.CertificateException {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[0];
        }
    };

    static final TrustManager[] X509_MANAGERS = new TrustManager[] { X509 };

    public BogusTrustManagerFactory() {
    }

    @Override
    protected TrustManager[] engineGetTrustManagers() {
        return X509_MANAGERS;
    }

    @Override
    protected void engineInit(KeyStore keystore) throws KeyStoreException {
        // noop
    }

    @Override
    protected void engineInit(ManagerFactoryParameters managerFactoryParameters)
            throws InvalidAlgorithmParameterException {
        // noop
    }
}

