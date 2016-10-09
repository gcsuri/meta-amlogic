SSUMMARY = "Amlogic audio video utils library"
PACKAGE_ARCH = "${MACHINE_ARCH}"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

#PR = "2fba80c"
#PD = "2fba80c5b6ac9b58c644b62cfac5f6f97f58bc70"

PR = "5e23a81"
PD = "5e23a81b706f1748e5054d2cc60be92f84c0fb21"

SRC_URI[md5sum] = "86cf810c10ed8bd99ec25a888b65a2af"
SRC_URI[sha256sum] = "412cfafbd9725f5186b884b9599ff6561d2031b44d9873e79d377631a2b5f9b9"

inherit lib_package

SRC_URI = "https://github.com/surkovalex/libamcodec/archive/${PR}.tar.gz"

S = "${WORKDIR}/libamcodec-${PD}/amavutils"

EXTRA_OEMAKE = "\
    'CC=${CC}' \
    'CFLAGS=-O2 -fPIC -DALSA_OUT -DENABLE_WAIT_FORMAT -I${S}/../amcodec/include -I${S}/include -I${S}' \
    'LDFLAGS=-shared -lm -lrt' \
"

do_install() {
    install -d ${D}${includedir}
    install -d ${D}${libdir}
    cp -PR ${S}/include ${D}/usr
    install -m 0755 ${S}/libamavutils.so ${D}/${libdir}
}

FILES_${PN} = "${includedir}/* ${libdir}/* "
FILES_${PN}-dev = "/usr/include/*"
FILES_${PN}-src = ""
