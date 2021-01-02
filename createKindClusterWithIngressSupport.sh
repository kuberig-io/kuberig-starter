# This script creates a local Kind kubernetes cluster with Ingress support by installing the nginx ingress controller.

# 1. Create a kind cluster with additional published ports.
# 2. Execute the mandatory command from the nginx ingress controller deploy instructions.
# 3. Install the nodeport service for bare-metal deployments (only needed to make the deployment work - not really used).
# 4. Patch the nginx ingress controller as per Kind Ingress install instructions.
#

kind create cluster --config src/main/yaml/kind-ingress/kind-ingress-cluster.yaml --image=kindest/node:v1.20.0
kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/master/deploy/static/provider/kind/deploy.yaml
sleep 10
kubectl wait --namespace kube-system --for=condition=ready pod/etcd-kind-control-plane --timeout=90s
kubectl wait --namespace kube-system --for=condition=ready pod/kube-apiserver-kind-control-plane --timeout=90s
kubectl wait --namespace kube-system --for=condition=ready pod/kube-controller-manager-kind-control-plane --timeout=90s
kubectl wait --namespace kube-system --for=condition=ready pod/kube-scheduler-kind-control-plane --timeout=90s
kubectl wait --namespace ingress-nginx --for=condition=ready pod --selector=k8s-app=kube-dns --timeout=90s
kubectl wait --namespace ingress-nginx --for=condition=ready pod --selector=app.kubernetes.io/component=controller --timeout=90s
kubectl get pods --all-namespaces