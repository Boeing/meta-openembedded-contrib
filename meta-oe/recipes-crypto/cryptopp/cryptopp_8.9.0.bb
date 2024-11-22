SUMMARY = "A C++ class library of cryptographic schemes"
DESCRIPTION = "Crypto++ Library is a C++ class library of cryptographic schemes"
CVE_PRODUCT = "cryptopp"
HOMEPAGE = "http://www.cryptopp.com/"
SECTION = "libs"
LICENSE = "BSL-1.0"
LIC_FILES_CHKSUM = "file://License.txt;md5=ca6dacb7ef68b1ab5dfa3bed24c12003"

SRC_URI = "git://github.com/weidai11/cryptopp.git;protocol=https;branch=master"
SRCREV = "843d74c7c97f9e19a615b8ff3c0ca06599ca501b"

S = "${UNPACKDIR}/git"

inherit pkgconfig

CXXFLAGS:append = " -fPIC"
# Avoid target mismatch error
CXXFLAGS:append:aarch64 = " -DCRYPTOPP_DISABLE_ASM=1"
EXTRA_OEMAKE += "HAS_SOLIB_VERSION=1"

do_compile () {
    oe_runmake -f GNUmakefile-cross libcryptopp.a libcryptopp.so cryptest.exe
}

do_install () {
    DESTDIR="${D}" \
        BINDIR="${bindir}" \
        DATADIR="${datadir}" \
        INCLUDEDIR="${includedir}" \
        LIBDIR="${libdir}" \
        oe_runmake install

    # Rename cryptest.exe to cryptest
    if [ -f "${D}${bindir}/cryptest.exe" ]; then
        mv "${D}${bindir}/cryptest.exe" "${D}${bindir}/cryptest"
    fi
}

PACKAGE_BEFORE_PN += "${PN}-cryptest ${PN}-testdata ${PN}-testvectors"

FILES:${PN}-cryptest = "${bindir}/cryptest"
FILES:${PN}-testdata = "${datadir}/cryptopp/TestData"
FILES:${PN}-testvectors = "${datadir}/cryptopp/TestVectors"

BBCLASSEXTEND = "native nativesdk"
