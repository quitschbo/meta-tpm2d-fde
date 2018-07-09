DECRIPTION = "Minimal initramfs-based root file system for tpm2d-based FDE"

PACKAGE_INSTALL = "\
	busybox \
	tpm2d-fde-boot \
	udev \
	base-passwd \
	ibmtss2 \
	lvm2 \
	kpartx \
	tpm2d \
	${ROOTFS_BOOTSTRAP_INSTALL} \
"
#packagegroup-core-boot 
#${VIRTUAL-RUNTIME_base-utils}

IMAGE_LINUGUAS = " "

LICENSE = "GPLv2"

IMAGE_FEATURES = ""

#EXTRA_IMAGE_FEATURES = "debug-tweaks "

export IMAGE_BASENAME = "tpm2-fde-initramfs"
IMAGE_FSTYPES = "${INITRAMFS_FSTYPES}"
inherit image

IMAGE_FEATURES_remove += "package-management"

IMAGE_ROOTFS_SIZE = "4096"

update_inittab () {
    echo "1:2345:respawn:${base_sbindir}/mingetty --autologin root tty1" >> ${IMAGE_ROOTFS}/etc/inittab
}

ROOTFS_POSTPROCESS_COMMAND += "update_inittab;"

#EXTRA_USERS_PARAMS = "usermod -P root root;"
