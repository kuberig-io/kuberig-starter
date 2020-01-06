# This script creates a local Kind kubernetes cluster with Ingress support by installing the nginx ingress controller.

# 1. Create a kind cluster with additional published ports.
# 2. Execute the mandatory command from the nginx ingress controller deploy instructions.
# 3. Install the nodeport service for bare-metal deployments (only needed to make the deployment work - not really used).
# 4. Patch the nginx ingress controller as per Kind Ingress install instructions.
#

kind create cluster --config src/main/yaml/kind-ingress/kind-ingress-cluster.yaml
kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/nginx-0.26.2/deploy/static/mandatory.yaml
kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/nginx-0.26.2/deploy/static/provider/baremetal/service-nodeport.yaml
kubectl patch deployments -n ingress-nginx nginx-ingress-controller -p '{"spec":{"template":{"spec":{"containers":[{"name":"nginx-ingress-controller","ports":[{"containerPort":80,"hostPort":80},{"containerPort":443,"hostPort":443}]}],"nodeSelector":{"ingress-ready":"true"},"tolerations":[{"key":"node-role.kubernetes.io/master","operator":"Equal","effect":"NoSchedule"}]}}}}'