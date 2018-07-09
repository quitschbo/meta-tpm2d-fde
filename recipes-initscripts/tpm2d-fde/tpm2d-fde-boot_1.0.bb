SUMMARY = "init script to start tpm2d fde encryption"
LICENSE = "MIT"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
SRC_URI = "file://init-tpm2d-fde-boot.sh"

PR = "r2"

S = "${WORKDIR}"

do_install() {
        install -m 0755 ${WORKDIR}/init-tpm2d-fde-boot.sh ${D}/init
        install -d ${D}/dev
        mknod -m 622 ${D}/dev/console c 5 1
        mknod -m 622 ${D}/dev/tty0 c 4 0
}

FILES_${PN} += " /init /dev "

# Due to kernel dependency
PACKAGE_ARCH = "${MACHINE_ARCH}"
