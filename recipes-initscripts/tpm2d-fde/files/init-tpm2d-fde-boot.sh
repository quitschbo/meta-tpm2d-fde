#!/bin/sh

PATH=/sbin:/bin:/usr/sbin:/usr/bin

CRYPTO_HDD=sda2

mkdir -p /proc
mkdir -p /sys

mount -t proc proc /proc
mount -t sysfs sysfs /sys
mount -t devtmpfs none /dev

mkdir -p /run
mkdir -p /var/run

udevd --daemon
udevadm trigger --action=add

mkdir -p /data/cml/tpm2d
tpm2d &

while [ ! -e /dev/${CRYPTO_HDD} ]; do
	echo Waiting for /dev/${CRYPTO_HDD}
	sleep 1
done

# give kernel some extra time to setup stuff, so
# we get a clear console for user promt
sleep 2
clear

read -s -p "Enter FDE Password: " passwd

tpm2_control dmcrypt_setup /dev/${CRYPTO_HDD} ${passwd}
tpm2_control exit

while [ ! -e /dev/mapper/${CRYPTO_HDD} ]; do
	echo Waiting for /dev/mapper/${CRYPTO_HDD}
	sleep 1
done
kpartx -a /dev/mapper/${CRYPTO_HDD}

while [ ! -e /dev/mapper/encrypted-root ]; do
	echo Waiting for /dev/mapper/encrypted-root
	sleep 1
done
mount /dev/mapper/encrypted-root /mnt

udevadm control --exit

exec switch_root -c /dev/console /mnt /sbin/init
