DESCRIPTION = "Amlogic Kernel"
SECTION = "kernel"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://${S}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

inherit kernel machine_kernel_pr

# MACHINE_KERNEL_PR_append = ".3"
RELEASE = "2016101205"
PV = "3.14.${RELEASE}"

DEPENDS = "xz-native bc-native u-boot-mkimage-native gcc"

# Avoid issues with Amlogic kernel binary components
INSANE_SKIP_${PN} += "already-stripped"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_STRIP = "1"
LINUX_VERSION ?= "3.14.y"
LINUX_VERSION_EXTENSION ?= "amlogic"

COMPATIBLE_MACHINE = "(s905)"

SRC_URI[md5sum] = "5502614061b69d8e275edfa01e043d35"
SRC_URI[sha256sum] = "00633be5bb4104010dfa0ba814e1739579f09298093fa08949473328614acafd"

SRC_URI = "https://github.com/gcsuri/linux-amlogic/archive/${RELEASE}.tar.gz \
    file://defconfig \
    file://boot.ini \
    file://boot.dtb \
    file://uInitrd \
    file://aml_autoscript \
"

S = "${WORKDIR}/linux-amlogic-${RELEASE}"
B = "${WORKDIR}/build"


do_compile_append () {
	install -d ${DEPLOY_DIR_IMAGE}
        install -m 0644 ${WORKDIR}/boot.ini ${DEPLOY_DIR_IMAGE}/boot.ini
        install -m 0644 ${WORKDIR}/uInitrd ${DEPLOY_DIR_IMAGE}/uInitrd
        install -m 0644 ${WORKDIR}/aml_autoscript ${DEPLOY_DIR_IMAGE}/aml_autoscript
        install -m 0644 ${WORKDIR}/boot.dtb ${DEPLOY_DIR_IMAGE}/boot.dtb
}

do_rm_work() {
}
