DESCRIPTION = "A TCP/IP Daemon simplifying the communication with GPS devices"
SECTION = "console/network"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=d217a23f408e91c94359447735bc1800"
DEPENDS = "dbus dbus-glib ncurses python libusb1"
PROVIDES = "virtual/gpsd"

PR = "r0"

SRC_URI = "http://download.savannah.gnu.org/releases/gpsd/gpsd-${PV}.tar.gz;name=gpsd \
           file://gpsd-default \
           file://gpsd \
           file://gpsd.socket \
           file://gpsd.service \
           file://60-gpsd.rules"
SRC_URI[gpsd.md5sum] = "c01353459faa68834309109d4e868460"
SRC_URI[gpsd.sha256sum] = "79f7de9ead63c7f5d2c9a92e85b5f82e53323c4d451ef8e27ea265ac3ef9a70f"

inherit scons update-rc.d python-dir systemd

INITSCRIPT_NAME = "gpsd"
INITSCRIPT_PARAMS = "defaults 35"

SYSTEMD_PACKAGES = "${PN}-systemd"
SYSTEMD_SERVICE = "${PN}.socket"


LDFLAGS += "-L${STAGING_LIBDIR} -lm"
export STAGING_INCDIR
export STAGING_LIBDIR

CC += "${LDFLAGS}"

EXTRA_OESCONS = " \
  libQgpsmm='false' \
  debug='true' \
  strip='false' \
  systemd='true' \
  pkgconfig='${PKG_CONFIG_DIR}' \
"

do_compile_prepend() {
    export LIBPATH="${STAGING_LIBDIR}"
    export PKG_CONFIG_PATH="${PKG_CONFIG_PATH}"
    export STAGING_PREFIX="${STAGING_DIR_HOST}/${prefix}"
    export BUILD_SYS="${BUILD_SYS}"
    export HOST_SYS="${HOST_SYS}"
}

do_install_prepend() {
    export PKG_CONFIG_PATH="${PKG_CONFIG_PATH}"
    export STAGING_PREFIX="${STAGING_DIR_HOST}/${prefix}"

    export BUILD_SYS="${BUILD_SYS}"
    export HOST_SYS="${HOST_SYS}"
}

do_install_append() {
    install -d ${D}/${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/gpsd ${D}/${sysconfdir}/init.d/
    install -d ${D}/${sysconfdir}/default
    install -m 0644 ${WORKDIR}/gpsd-default ${D}/${sysconfdir}/default/gpsd.default

    #support for udev
    install -d ${D}/${sysconfdir}/udev/rules.d
    install -m 0644 ${WORKDIR}/60-gpsd.rules ${D}/${sysconfdir}/udev/rules.d
    install -d ${D}${base_libdir}/udev/
    install -m 0755 ${S}/gpsd.hotplug ${D}${base_libdir}/udev/
    install -d ${D}${base_libdir}/udev/

    #support for python
    install -d ${D}/${PYTHON_SITEPACKAGES_DIR}/gps
    for f in ${S}/gps/* ;do
        install $f ${D}/${PYTHON_SITEPACKAGES_DIR}/gps
    done
}

pkg_postinst_${PN}-conf() {
	update-alternatives --install ${sysconfdir}/default/gpsd gpsd-defaults ${sysconfdir}/default/gpsd.default 10
}

pkg_postrm_${PN}-conf() {
	update-alternatives --remove gpsd-defaults ${sysconfdir}/default/gpsd.default	
}

PACKAGES =+ "libgps libgpsd python-pygps-dbg python-pygps gpsd-udev gpsd-conf gpsd-gpsctl gps-utils"

FILES_python-pygps-dbg += " ${libdir}/python*/site-packages/gps/.debug"

RDEPENDS_${PN} = "gpsd-gpsctl"
RRECOMMENDS_${PN} = "gpsd-conf gpsd-udev"

DESCRIPTION_gpsd-udev = "udev relevant files to use gpsd hotplugging"
FILES_gpsd-udev = "${base_libdir}/udev ${sysconfdir}/udev/*"
RDEPENDS_gpsd-udev += "udev gpsd-conf"

DESCRIPTION_libgpsd = "C service library used for communicating with gpsd"
FILES_libgpsd = "${libdir}/libgpsd.so.*"

DESCRIPTION_libgps = "C service library used for communicating with gpsd"
FILES_libgps = "${libdir}/libgps.so.*"

DESCRIPTION_gpsd-conf = "gpsd configuration files and init scripts"
FILES_gpsd-conf = "${sysconfdir}"

DESCRIPTION_gpsd-gpsctl = "Tool for tweaking GPS modes"
FILES_gpsd-gpsctl = "${bindir}/gpsctl"

DESCRIPTION_gps-utils = "Utils used for simulating, monitoring,... a GPS"
FILES_gps-utils = "${bindir}/*"
RDEPENDS_gps-utils = "python-pygps"

DESCRIPTION_python-pygps = "Python bindings to gpsd"
FILES_python-pygps = "${PYTHON_SITEPACKAGES_DIR}/*"
RDEPENDS_python-pygps = "python-core python-curses gpsd python-json"
