package ingress

import io.kuberig.annotations.EnvResources
import io.kuberig.dsl.support.DslResourceEmitter.emit
import kinds.cert_manager.io.v1.certificate
import kinds.cert_manager.io.v1.issuer

/**
 * Deploy cert-manager.
 *
 * $ kubectl apply -f https://github.com/jetstack/cert-manager/releases/download/v1.1.0/cert-manager.yaml
 *
 * Depending on your kubernetes cluster it may be needed to grant cluster-admin to the kuberig service account for
 * this example to work.
 *
 * $ kubectl create clusterrolebinding cluster-admin-binding --clusterrole=cluster-admin --serviceaccount=default:kuberig
 */
class CertManagerResources {

    @EnvResources
    fun certManagerIssuer() {
        emit(
            issuer {
                metadata {
                    name("test-selfsigned")
                }
                spec {
                    selfSigned {

                    }
                }
            }
        )

        emit(
            certificate {
                metadata {
                    name("selfsigned-cert")
                }
                spec {
                    dnsNames {
                        dnsName("example.com")
                    }
                    secretName("selfsigned-cert-tls")
                    issuerRef {
                        name("test-selfsigned")
                    }
                }
            }
        )
    }
}