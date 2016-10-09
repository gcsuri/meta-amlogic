SUMMARY = "Amlogic codecs library"
PACKAGE_ARCH = "${MACHINE_ARCH}"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

PR = "5e23a81"
PD = "5e23a81b706f1748e5054d2cc60be92f84c0fb21"
PV = "0.2"

#PR = "2fba80c"
#PD = "2fba80c5b6ac9b58c644b62cfac5f6f97f58bc70"

#SRC_URI[md5sum] = "86cf810c10ed8bd99ec25a888b65a2af"
#SRC_URI[sha256sum] = "412cfafbd9725f5186b884b9599ff6561d2031b44d9873e79d377631a2b5f9b9"

DEPENDS = "libamadec"
RDEPENDS_${PN} = "libamadec"

inherit lib_package

SRC_URI = "https://github.com/surkovalex/libamcodec/archive/${PR}.tar.gz \
           file://libamcodec.pc \
	   file://00-makefile-fix.patch \
           file://alsactl.conf \
"

S = "${WORKDIR}/libamcodec-${PD}/amcodec"

EXTRA_OEMAKE = " \
    'CC=${CC}' \
    'CFLAGS=-O2 -fPIC -I${S}/include -I${S} -I${S}/codec -I${STAGING_INCDIR}/amlogic/amadec -I${S}/../amffmpeg' \
    'LDFLAGS=-lamadec -lm -lc  -shared -Wl,--shared -Wl,-soname,libamcodec.so' \
"

do_install() {
    install -d ${D}${libdir}/pkgconfig
    install -d ${D}${sysconfdir}/
    install -m 0644 ${WORKDIR}/alsactl.conf ${D}${sysconfdir}/
    install -m 0644 ${WORKDIR}/libamcodec.pc ${D}${libdir}/pkgconfig/
    install -d ${D}${includedir}/amlogic/amcodec
    cp -pR ${S}/include/* ${D}${includedir}/amlogic/amcodec
    install -d ${D}${libdir}
    install -m 0755  ${S}/libamcodec.so.0.0  ${D}${libdir}
    cd ${D}${libdir}
    ln -sf libamcodec.so.0.0 libamcodec.so
}
