package ingress

import io.kuberig.annotations.EnvResources
import io.kuberig.dsl.support.DslResourceEmitter.emit
import kinds.cert_manager.io.v1.certificate
import kinds.cert_manager.io.v1.issuer

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