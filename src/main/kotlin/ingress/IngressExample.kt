package ingress

import io.kuberig.annotations.EnvResource
import kinds.extensions.v1beta1.IngressDsl
import kinds.extensions.v1beta1.ingress
import kinds.v1.PodDsl
import kinds.v1.ServiceDsl
import kinds.v1.pod
import kinds.v1.service

/**
 * Based on the <a href="https://kind.sigs.k8s.io/docs/user/ingress/">Using Ingress</a> section of the Kind docs.
 *
 * This is only a one-on-one mapping!
 *
 * Too learn how the KubeRig DSL can take it to the next level check out the the <a href="https://teyckmans.github.io/kuberig/#/dsl-intro">DSL intro</a>.
 */
class IngressExample {

    @EnvResource
    fun fooApp(): PodDsl {
        return pod {
            metadata {
                name("foo-app")
                labels {
                    label("app", "foo")
                }
            }
            spec {
                containers {
                    container {
                        name("foo-app")
                        image("hashicorp/http-echo")
                        args {
                            arg("-text=foo")
                        }
                    }
                }
            }
        }
    }

    @EnvResource
    fun fooService(): ServiceDsl {
        return service {
            metadata {
                name("foo-service")
            }
            spec {
                selector("app", "foo")
                ports {
                    port {
                        // Default port used by the image
                        port(5678)
                    }
                }
            }
        }
    }

    @EnvResource
    fun barApp(): PodDsl {
        return pod {
            metadata {
                name("bar-app")
                labels {
                    label("app", "bar")
                }
            }
            spec {
                containers {
                    container {
                        name("bar-app")
                        image("hashicorp/http-echo")
                        args {
                            arg("-text=bar")
                        }
                    }
                }
            }
        }
    }

    @EnvResource
    fun barService(): ServiceDsl {
        return service {
            metadata {
                name("bar-service")
            }
            spec {
                selector("app", "bar")
                ports {
                    port {
                        // Default port used by the image
                        port(5678)
                    }
                }
            }
        }
    }

    @EnvResource
    fun exampleIngress(): IngressDsl {
        return ingress {
            metadata {
                name("example-ingress")
                annotations {
                    annotation("ingress.kubernetes.io/rewrite-target", "/")
                }
            }
            spec {
                rules {
                    rule {
                        http {
                            paths {
                                path {
                                    path("/foo")
                                    backend {
                                        serviceName("foo-service")
                                        servicePort(5678)
                                    }
                                }
                                path {
                                    path("/bar")
                                    backend {
                                        serviceName("bar-service")
                                        servicePort(5678)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}