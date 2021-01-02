plugins {
  id("io.kuberig.kuberig")
}

repositories {
  jcenter()
  maven("https://dl.bintray.com/teyckmans/rigeldev-oss-maven")
}

kuberig {
  kubernetes("v1.20.0")
}

dependencies {
  implementation("io.kuberig.crd.dsl:cert-manager:0.1.0")
}