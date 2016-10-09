SUMMARY = "Amlogic player library"
PACKAGE_ARCH = "${MACHINE_ARCH}"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

PR = "5e23a81"
PD = "5e23a81b706f1748e5054d2cc60be92f84c0fb21"

#PR = "2fba80c"
#PD = "2fba80c5b6ac9b58c644b62cfac5f6f97f58bc70"

SRC_URI[md5sum] = "86cf810c10ed8bd99ec25a888b65a2af"
SRC_URI[sha256sum] = "412cfafbd9725f5186b884b9599ff6561d2031b44d9873e79d377631a2b5f9b9"

DEPENDS = "libamadec libamcodec"
RDEPENDS_${PN} = "libamadec libamcodec"

inherit lib_package

SRC_URI = "https://github.com/surkovalex/libamcodec/archive/${PR}.tar.gz \
           file://00-amplayer-makefile-fixes.patch \
           file://libamplayer.pc \
"

S = "${WORKDIR}/libamcodec-${PD}/amplayer"

EXTRA_OEMAKE = "\
    'CC=${CC}' \
    'CFLAGS=-fPIC -DENABLE_FREE_SCALE -I${S}/include -I${S}/../amffmpeg -I${S}/player/ \
    -I${S}/player/include -I${S} -I${STAGING_INCDIR}/amlogic/amcodec' \
    'LDFLAGS=-lamadec -lm -lc  -shared -Wl,--shared ' \
"

do_install() {
    install -d ${D}${libdir}/pkgconfig
    install -m 0644 ${WORKDIR}/libamplayer.pc ${D}${libdir}/pkgconfig/
    install -d ${D}${includedir}/amlogic/player
    install -d ${D}${libdir}
    install -m 0755  ${S}/libamplayer.so  ${D}${libdir}
    cp -pR  ${S}/player/include/* ${D}${includedir}/amlogic/player
}

FILES_${PN} = "${libdir}/* "
FILES_${PN}-dev = "${includedir}/*"
