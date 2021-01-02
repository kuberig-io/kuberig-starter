plugins {
  id("io.kuberig.kuberig")
}

repositories {
  jcenter()
}

kuberig {
  kubernetes("v1.20.0")
}
