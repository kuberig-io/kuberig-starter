# Ingress Example

Open a terminal in the project root directory.

## Create the cluster

Create a local kind cluster with Ingress support by executing the following command.
```shell script
$ ./createKindClusterWithIngressSupport.sh
```

## Create the KubeRig environment

Create the localdev environment.
```shell script
$ ./gradlew initEnvironment --currentKubectlContext --name localdev
```

## Deploy the example ingress

Deploy the example ingress resources. The definitions can be found in src/main/kotlin/ingress/IngressExample.kt.
```shell script
$ ./gradlew deployLocaldevEnvironment
```

## Test the example ingress

Try the example ingress.
```shell script
$ curl localhost/foo
$ curl localhost/bar
```

## Cleanup

Delete the cluster
```shell script
$ kind delete cluster
```
