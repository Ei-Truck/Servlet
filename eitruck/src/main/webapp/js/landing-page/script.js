let paginaAtual = 1;

function mudarPagina(num, scrollPos = 0) {
    // Esconde todos os conteúdos
    document.querySelectorAll('.conteudo').forEach(div => div.classList.remove('ativo'));

    // Mostra apenas o escolhido
    const conteudoAtivo = document.getElementById("pagina" + num);
    conteudoAtivo.classList.add("ativo");

    // Atualiza estilo dos botões
    document.querySelectorAll('.paginacao button').forEach(btn => btn.classList.remove('ativo'));
    document.querySelector(`.paginacao button:nth-child(${num})`).classList.add('ativo');

    paginaAtual = num;

    if (scrollPos !== null) {
        window.scrollTo({ top: scrollPos, behavior: 'smooth' });
    }
}

function mudarPaginaNav(num) {
    mudarPagina(num, null);
    setTimeout(() => {
        window.scrollTo({ top: 600 });
    }, 0);
}

function mudarPaginaNavSobre() {
    mudarPagina(3, null);
    setTimeout(() => {
        window.scrollTo({ top: 2160 });
    }, 0);
}

function mudarPaginaNavSaibaMais() {
    mudarPagina(1, null);
    setTimeout(() => {
        window.scrollTo({ top: 600 });
    }, 0);
}

function mudarPaginaNavTelemetria() {
    mudarPagina(1, null);
    setTimeout(() => {
        const elemento = document.getElementById('como-funciona');
        if (elemento) {
            const offset = elemento.offsetTop - 72;
            window.scrollTo({ top: offset, behavior: 'smooth' });
        }
    }, 100);
}

function mudarPaginaNavFuncionalidades() {
    mudarPagina(1, null);
    setTimeout(() => {
        window.scrollTo({ top: 600 });
    }, 0);
}

// Header muda para branco ao rolar
const headerEl = document.getElementById('header');
function updateHeader() {
    const threshold = window.innerHeight * 0.15;
    if (window.scrollY > threshold) { headerEl.classList.add('header-white'); }
    else { headerEl.classList.remove('header-white'); }
}
window.addEventListener('scroll', updateHeader, { passive: true });
window.addEventListener('load', updateHeader);

// Tabs de "Transforme os dados"
function initTabs(section) {
    const tabs = section.querySelectorAll('.tab');

    // Encontrar os painéis específicos desta seção
    const panelVideo = section.querySelector('[id^="panel-video"]');
    const panelTele = section.querySelector('[id^="panel-tele"]');

    if (!tabs.length || !panelVideo || !panelTele) return;

    tabs.forEach(btn => btn.addEventListener('click', () => {
        tabs.forEach(b => b.classList.remove('active'));
        btn.classList.add('active');

        if (btn.dataset.tab === 'video') {
            panelVideo.classList.remove('hide');
            panelTele.classList.add('hide');
        } else {
            panelTele.classList.remove('hide');
            panelVideo.classList.add('hide');
        }
    }));
}


// Abas grandes dos produtos
const bigTabs = document.querySelectorAll('.big-tab');
const camPanels = {
    multi: document.getElementById('cam-multi'),
    fadiga: document.getElementById('cam-fadiga'),
    cabine: document.getElementById('cam-cabine')
};

bigTabs.forEach(bt => bt.addEventListener('click', () => {
    bigTabs.forEach(b => b.classList.remove('active'));
    bt.classList.add('active');
    const key = bt.dataset.cam;
    Object.keys(camPanels).forEach(k => camPanels[k].classList.remove('active'));
    camPanels[key].classList.add('active');
}));

window.onload = function () {
    mudarPagina(1);

    // Inicializa tabs para cada página
    document.querySelectorAll('.conteudo').forEach(section => {
        initTabs(section);
    });
};

document.addEventListener('DOMContentLoaded', function () {
    const transformSections = document.querySelectorAll('.transform');

    const observerOptions = {
        root: null,
        rootMargin: '0px',
        threshold: 0.2
    };

    const observer = new IntersectionObserver(function (entries, observer) {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.classList.add('animated');

                setTimeout(() => {
                    const activePanel = entry.target.querySelector('.panel:not(.hide)');
                    if (activePanel) {
                        activePanel.classList.add('active-panel');
                    }
                }, 500);

                observer.unobserve(entry.target);
            }
        });
    }, observerOptions);

    transformSections.forEach(section => {
        observer.observe(section);
    });

    // Atualizar os event listeners para funcionar com qualquer seção transform
    document.querySelectorAll('.transform .tab').forEach(tab => {
        tab.addEventListener('click', function () {
            const tabType = this.dataset.tab;
            const transformSection = this.closest('.transform');
            const panels = transformSection.querySelectorAll('.panel');

            transformSection.querySelectorAll('.tab').forEach(t => t.classList.remove('active'));

            this.classList.add('active');

            panels.forEach(panel => {
                panel.classList.add('hide');
                panel.classList.remove('active-panel');
            });

            // Usar seletor que funciona para qualquer página
            const targetPanel = transformSection.querySelector(`[id^="panel-${tabType}"]`);
            if (targetPanel) {
                targetPanel.classList.remove('hide');
            }

            setTimeout(() => {
                if (targetPanel) {
                    targetPanel.classList.add('active-panel');
                }
            }, 50);
        });
    });
});

let resizeTimeout;
window.addEventListener('resize', function () {
    clearTimeout(resizeTimeout);
    resizeTimeout = setTimeout(function () {
        if (typeof getScrollPosition === 'function') {
            console.log('Janela redimensionada, posições atualizadas');
        }
    }, 250);
});

function getScrollPosition(target) {
    let screenWidth = window.innerWidth;

    // Valores padrão para desktop
    let positions = {
        'hero': 0,
        'solucoes': 600,
        'transforme': 2850,
        'diferenciais': 2150,
        'como-funciona': 2400,
        'planos': 3000
    };

    // Ajustes para tablet
    if (screenWidth <= 1024) {
        positions = {
            'hero': 0,
            'solucoes': 0,
            'transforme': 0,
            'diferenciais': 2200,
            'como-funciona': 2800,
            'planos': 0
        };
    }

    // Ajustes para mobile
    if (screenWidth <= 900) {
        positions = {
            'hero': 0,
            'solucoes': 10000,
            'transforme': 4450,
            'diferenciais': 3400,
            'como-funciona': 2800,
            'planos': 2000
        };
    }

    if (screenWidth <= 410) {
        positions = {
            'hero': 0,
            'solucoes': 10000,
            'transforme': 4650,
            'diferenciais': 0,
            'como-funciona': 1600,
            'planos': 2000
        };
    }

    return positions[target] || 0;
}

function navigateToProduct() {
    const screenWidth = window.innerWidth;

    // Desktop (acima de 1024px)
    if (screenWidth > 1024) {
        mudarPaginaNav(1);
        setTimeout(() => {
            window.scrollTo({ top: 600, behavior: 'smooth' });
        }, 100);
    }
    // Tablet (768px a 1024px)
    else if (screenWidth >= 900 && screenWidth <= 1024) {
        mudarPaginaNav(1);
        setTimeout(() => {
            window.scrollTo({ top: 550, behavior: 'smooth' });
        }, 100);
    }

    else if (screenWidth >= 410 && screenWidth <= 900) {
        mudarPaginaNav(1);
        setTimeout(() => {
            window.scrollTo({ top: 1150, behavior: 'smooth' });
        }, 100);
    }

    // Mobile (abaixo de 768px)
    else {
        mudarPaginaNav(1);
        setTimeout(() => {
            window.scrollTo({ top: 6000, behavior: 'smooth' });
        }, 100);
    }
}

function navigateToSearch() {
    const screenWidth = window.innerWidth;

    // Desktop (acima de 1024px)
    if (screenWidth > 1024) {
        mudarPaginaNav(2);
        setTimeout(() => {
            window.scrollTo({ top: 600, behavior: 'smooth' });
        }, 100);
    }
    // Tablet (768px a 1024px)
    else if (screenWidth >= 900 && screenWidth <= 1024) {
        mudarPaginaNav(2);
        setTimeout(() => {
            window.scrollTo({ top: 550, behavior: 'smooth' });
        }, 100);
    }

    else if (screenWidth >= 410 && screenWidth <= 900) {
        mudarPaginaNav(2);
        setTimeout(() => {
            window.scrollTo({ top: 1150, behavior: 'smooth' });
        }, 100);
    }

    // Mobile (abaixo de 768px)
    else {
        mudarPaginaNav(2);
        setTimeout(() => {
            window.scrollTo({ top: 400, behavior: 'smooth' });
        }, 100);
    }
}


function navigateToGerminare() {
    const screenWidth = window.innerWidth;

    // Desktop (acima de 1024px)
    if (screenWidth > 1024) {
        mudarPaginaNav(3);
        setTimeout(() => {
            window.scrollTo({ top: 600, behavior: 'smooth' });
        }, 100);
    }
    // Tablet (768px a 1024px)
    else if (screenWidth >= 900 && screenWidth <= 1024) {
        mudarPaginaNav(3);
        setTimeout(() => {
            window.scrollTo({ top: 550, behavior: 'smooth' });
        }, 100);
    }

    else if (screenWidth >= 410 && screenWidth <= 900) {
        mudarPaginaNav(3);
        setTimeout(() => {
            window.scrollTo({ top: 1150, behavior: 'smooth' });
        }, 100);
    }
    // Mobile (abaixo de 768px)
    else {
        mudarPaginaNav(3);
        setTimeout(() => {
            window.scrollTo({ top: 400, behavior: 'smooth' });
        }, 100);
    }
}

function navigateToAbout1() {
    const screenWidth = window.innerWidth;

    // Desktop (acima de 1024px)
    if (screenWidth > 1024) {
        mudarPaginaNav(3);
        setTimeout(() => {
            window.scrollTo({ top: 1950, behavior: 'smooth' });
        }, 100);
    }
    // Tablet (768px a 1024px)
    else if (screenWidth >= 900 && screenWidth <= 1024) {
        mudarPaginaNav(3);
        setTimeout(() => {
            window.scrollTo({ top: 1250, behavior: 'smooth' });
        }, 100);
    }

    else if (screenWidth >= 410 && screenWidth <= 900) {
        mudarPaginaNav(3);
        setTimeout(() => {
            window.scrollTo({ top: 1250, behavior: 'smooth' });
        }, 100);
    }
    // Mobile (abaixo de 768px)
    else {
        mudarPaginaNav(3);
        setTimeout(() => {
            window.scrollTo({ top: 400, behavior: 'smooth' });
        }, 100);
    }
}

function navigateToAbout2() {
    const screenWidth = window.innerWidth;

    // Desktop (acima de 1024px)
    if (screenWidth > 1024) {
        mudarPaginaNav(1);
        setTimeout(() => {
            window.scrollTo({ top: 600, behavior: 'smooth' });
        }, 100);
    }
    // Tablet (768px a 1024px)
    else if (screenWidth >= 900 && screenWidth <= 1024) {
        mudarPaginaNav(1);
        setTimeout(() => {
            window.scrollTo({ top: 550, behavior: 'smooth' });
        }, 100);
    }

    else if (screenWidth >= 410 && screenWidth <= 900) {
        mudarPaginaNav(1);
        setTimeout(() => {
            window.scrollTo({ top: 1150, behavior: 'smooth' });
        }, 100);
    }

    // Mobile (abaixo de 768px)
    else {
        mudarPaginaNav(1);
        setTimeout(() => {
            window.scrollTo({ top: 6000, behavior: 'smooth' });
        }, 100);
    }
}

function navigateToFuncionalidades() {
    const screenWidth = window.innerWidth;

    // Desktop (acima de 1024px)
    if (screenWidth > 1024) {
        mudarPaginaNav(1);
        setTimeout(() => {
            window.scrollTo({ top: 600, behavior: 'smooth' });
        }, 100);
    }
    // Tablet (768px a 1024px)
    else if (screenWidth >= 900 && screenWidth <= 1024) {
        mudarPaginaNav(1);
        setTimeout(() => {
            window.scrollTo({ top: 550, behavior: 'smooth' });
        }, 100);
    }

    else if (screenWidth >= 410 && screenWidth <= 900) {
        mudarPaginaNav(1);
        setTimeout(() => {
            window.scrollTo({ top: 1150, behavior: 'smooth' });
        }, 100);
    }

    // Mobile (abaixo de 768px)
    else {
        mudarPaginaNav(1);
        setTimeout(() => {
            window.scrollTo({ top: 6000, behavior: 'smooth' });
        }, 100);
    }
}

function mudarPaginaNavResponsivo(num, target) {
    mudarPagina(num, null);
    setTimeout(() => {
        const position = getScrollPosition(target);
        window.scrollTo({ top: position, behavior: 'smooth' });
    }, 100);
}

function mudarPaginaNavSobreResponsivo() {
    mudarPagina(3, null);
    setTimeout(() => {
        const position = getScrollPosition('diferenciais');
        window.scrollTo({ top: position, behavior: 'smooth' });
    }, 100);
}

function mudarPaginaNavSaibaMaisResponsivo() {
    mudarPagina(1, null);
    setTimeout(() => {
        const position = getScrollPosition('solucoes');
        window.scrollTo({ top: position, behavior: 'smooth' });
    }, 100);
}

function mudarPaginaNavTelemetriaResponsivo() {
    mudarPagina(1, null);
    setTimeout(() => {
        const position = getScrollPosition('transforme');
        window.scrollTo({ top: position, behavior: 'smooth' });

        setTimeout(() => {
            const teleTab = document.querySelector('.tab[data-tab="tele"]');
            if (teleTab) teleTab.click();
        }, 500);
    }, 100);
}

function mudarPaginaNavFuncionalidadesResponsivo() {
    mudarPagina(1, null);
    setTimeout(() => {
        const position = getScrollPosition('solucoes');
        window.scrollTo({ top: position, behavior: 'smooth' });
    }, 100);
}

document.addEventListener('DOMContentLoaded', function () {
    const navLinks = document.querySelectorAll('nav a, footer a, footer h5');

    navLinks.forEach(link => {
        link.addEventListener('click', function (e) {
            e.preventDefault();

            const text = this.textContent.toLowerCase() || this.getAttribute('onclick') || '';

            if (text.includes('telemetria') || text.includes('fadiga') || text.includes('rastreamento') || text.includes('monitoramento')) {
                mudarPaginaNavTelemetriaResponsivo();
            } else if (text.includes('sobre')) {
                mudarPaginaNavSobreResponsivo();
            } else if (text.includes('saiba mais')) {
                mudarPaginaNavSaibaMaisResponsivo();
            } else if (text.includes('produtividade') || text.includes('controle') || text.includes('segurança') || text.includes('funcionalidades')) {
                mudarPaginaNavFuncionalidadesResponsivo();
            }
        });
    });

    if (window.innerWidth <= 576) {
        const footerHeaders = document.querySelectorAll('footer h5');
        footerHeaders.forEach(header => {
            header.addEventListener('click', function () {
                this.classList.toggle('ativo');
            });
        });
    }
});

window.addEventListener('resize', function () {
    const footerHeaders = document.querySelectorAll('footer h5');

    if (window.innerWidth <= 576) {
        footerHeaders.forEach(header => {
            header.addEventListener('click', function () {
                this.classList.toggle('ativo');
            });
        });
    } else {
        footerHeaders.forEach(header => {
            header.replaceWith(header.cloneNode(true));
            const uls = document.querySelectorAll('footer ul');
            uls.forEach(ul => ul.style.display = 'grid');
        });
    }
});

document.querySelector('.plan-cta').addEventListener('click', function() {
    alert('Ótima escolha! Redirecionando para o processo de contratação...');
});