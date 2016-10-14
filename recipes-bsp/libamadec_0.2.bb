SUMMARY = "Amlogic audio decoders library"
PACKAGE_ARCH = "${MACHINE_ARCH}"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

PR = "5e23a81"
PD = "5e23a81b706f1748e5054d2cc60be92f84c0fb21"

#SRC_URI[md5sum] = "86cf810c10ed8bd99ec25a888b65a2af"
#SRC_URI[sha256sum] = "412cfafbd9725f5186b884b9599ff6561d2031b44d9873e79d377631a2b5f9b9"

#DEPENDS = "libamavutils alsa-lib rtmpdump"
DEPENDS = "libamavutils alsa-lib "
RDEPENDS_${PN} = "libamavutils alsa-lib "

### for DTS encoder: don't check for stripped & text relocations
INSANE_SKIP_${PN} = "already-stripped textrel"

inherit lib_package

# SRC_URI = "https://github.com/surkovalex/libamcodec/archive/${PR}.tar.gz

SRC_URI = "file://libamcodec-210755d.tar.gz;md5=dd2153497a999a41cdc7f80f62e64543 \
           file://libamadec.pc \
"

S = "${WORKDIR}/libamcodec-210755d/amadec"

FWL_s905 = "firmware"

EXTRA_OEMAKE = "\
    'CC=${CC}' \
    'CFLAGS=-O2 -fPIC -pthread -DALSA_OUT -DENABLE_WAIT_FORMAT -I${S}/include -I${S} -I${S}/../amffmpeg' \
    'LDFLAGS=-shared -lamavutils -lpthread -lm -lasound -lrt -ldl' \
"

### NOTE: we are installing closed src DTS encoder as well for transcoding
do_install() {
    install -d ${D}${libdir}/pkgconfig
    install -d ${D}${includedir}/amlogic/amadec
    install -d ${D}${base_libdir}/firmware
    install -d ${D}${libdir}
    install -m 0755 ${S}/include/* ${D}${includedir}/amlogic/amadec
    install -m 0755 ${S}/libamadec.so ${D}/${libdir}
#    install -m 0755 ${S}/acodec_lib/*.so  ${D}/${libdir}
    install -m 0644 ${S}/firmware/*.bin  ${D}${base_libdir}/firmware/
#    install -m 0644 ${WORKDIR}/audiodsp_codec_ddp_dcv.bin ${D}${base_libdir}/firmware/
    install -m 0644 ${WORKDIR}/libamadec.pc ${D}${libdir}/pkgconfig/
}

FILES_${PN} = "${libdir}/* ${base_libdir}/firmware"
FILES_${PN}-dev = "${includedir}/*"
