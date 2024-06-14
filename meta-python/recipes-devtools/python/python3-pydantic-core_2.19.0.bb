SUMMARY = "Provides the core functionality for pydantic validation and serialization."
DESCRIPTION = "This package provides the core functionality for \
pydantic validation and serialization.\
\
Pydantic-core is currently around 17x faster than pydantic V1."
HOMEPAGE = "https://github.com/pydantic/pydantic-core"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ab599c188b4a314d2856b3a55030c75c"

SRC_URI[sha256sum] = "9ad77f1aecedef233390eff7fd643fe652e5ac92b793329424c2a1d2e28c08d1"

DEPENDS = "python3-maturin-native python3-typing-extensions"

require ${BPN}-crates.inc

inherit pypi cargo-update-recipe-crates python_maturin

PYPI_PACKAGE = "pydantic_core"

RDEPENDS:${PN} += "python3-typing-extensions"

INSANE_SKIP:${PN} = "already-stripped"

inherit ptest
SRC_URI += "file://run-ptest"
RDEPENDS:${PN}-ptest += "\
    python3-dateutil \
    python3-dirty-equals \
    python3-hypothesis \
    python3-pytest \
    python3-pytest-mock \
    python3-pytest-timeout \
    python3-pytest-benchmark \
    python3-tzdata \
    python3-unittest-automake-output \
    python3-zoneinfo \
"

do_install_ptest() {
    cp -rf ${S}/tests/ ${D}${PTEST_PATH}/
    sed -i -e "/--automake/ s/$/ -k 'not test_model_class_root_validator_wrap and not test_model_class_root_validator_before and not test_model_class_root_validator_after'/" ${D}${PTEST_PATH}/run-ptest
}
