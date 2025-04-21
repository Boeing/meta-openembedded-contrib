SUMMARY = "Simple DirectMedia Layer mixer library V2"
SECTION = "libs"
DEPENDS = "libsdl2 flac libvorbis opusfile libxmp fluidsynth wavpack"
LICENSE = "Zlib"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=fbb0010b2f7cf6e8a13bcac1ef4d2455"

SRC_URI = "http://www.libsdl.org/projects/SDL_mixer/release/SDL2_mixer-${PV}.tar.gz"
SRC_URI[sha256sum] = "cb760211b056bfe44f4a1e180cc7cb201137e4d1572f2002cc1be728efd22660"

S = "${WORKDIR}/SDL2_mixer-${PV}"

inherit cmake pkgconfig

do_configure:prepend() {
	# cmake checks for these binaries. Touch them to pass the tests and add RDEPENDS
	touch ${STAGING_BINDIR}/fluidsynth
	touch ${STAGING_BINDIR}/wavpack
	touch ${STAGING_BINDIR}/wvunpack
	touch ${STAGING_BINDIR}/wvgain
	touch ${STAGING_BINDIR}/wvtag
}

EXTRA_OECMAKE += "-DSDL2MIXER_VORBIS=VORBISFILE -DSDL2MIXER_VORBIS_VORBISFILE=ON"

FILES:${PN} += "${datadir}/licenses"

RDEPENDS:${PN} = "fluidsynth-bin wavpack-bin"
