/**
 * RacimoSano — lógica del cliente.
 * - En index.html: consume GET /api/plagas y arma las tarjetas del catálogo.
 * - En reportar.html: llena los <select> de lote y plaga desde la API, y
 *   envía el formulario como multipart/form-data (incluye archivos) a
 *   POST /api/reportes. La fecha ya no se envía: la asigna el backend.
 */

const ICONOS_POR_TIPO = {
  Hongo: { src: "img/hongo.svg", alt: "Icono que representa una enfermedad causada por un hongo" },
  Insecto: { src: "img/insecto.svg", alt: "Icono que representa una plaga tipo insecto" },
  Acaro: { src: "img/acaro.svg", alt: "Icono que representa una plaga tipo ácaro" }
};

const CLASE_BADGE_POR_NIVEL = {
  Alto: "bg-severe-600",
  Medio: "bg-amber-500 text-plum-900",
  Bajo: "bg-vine-600"
};

function crearTarjetaPlaga(plaga) {
  const articulo = document.createElement("article");
  articulo.className = "rounded-xl border border-plum-900/10 bg-white p-5 flex flex-col gap-2";
  articulo.dataset.tipo = plaga.tipo;

  const icono = ICONOS_POR_TIPO[plaga.tipo] || ICONOS_POR_TIPO.Insecto;

  const img = document.createElement("img");
  img.className = "w-11 h-11";
  img.src = icono.src;
  img.alt = icono.alt;
  articulo.appendChild(img);

  const badge = document.createElement("span");
  badge.className = "self-start rounded-full px-3 py-0.5 text-xs font-semibold text-white " +
    (CLASE_BADGE_POR_NIVEL[plaga.nivelDano] || "bg-amber-500");
  badge.textContent = "Daño " + plaga.nivelDano.toLowerCase();
  articulo.appendChild(badge);

  const titulo = document.createElement("h3");
  titulo.className = "font-display font-semibold text-lg text-plum-900";
  titulo.textContent = plaga.nombreComun;
  articulo.appendChild(titulo);

  const cientifico = document.createElement("p");
  cientifico.className = "italic text-plum-900/60 text-sm";
  cientifico.textContent = plaga.nombreCientifico;
  articulo.appendChild(cientifico);

  const sintomas = document.createElement("p");
  sintomas.className = "text-sm text-plum-900/90";
  sintomas.textContent = plaga.sintomas;
  articulo.appendChild(sintomas);

  const detalle = document.createElement("details");
  detalle.className = "mt-1 border-t border-plum-900/10 pt-2";
  const resumen = document.createElement("summary");
  resumen.className = "cursor-pointer font-semibold text-vine-700 text-sm";
  resumen.textContent = "Manejo recomendado";
  detalle.appendChild(resumen);
  const recomendacion = document.createElement("p");
  recomendacion.className = "text-sm mt-1 text-plum-900/90";
  recomendacion.textContent = plaga.recomendacion;
  detalle.appendChild(recomendacion);
  articulo.appendChild(detalle);

  return articulo;
}

function inicializarCatalogo() {
  const grid = document.getElementById("pest-grid");
  const estado = document.getElementById("catalog-status");
  const botonesFiltro = document.querySelectorAll(".filter-btn");

  if (!grid) {
    return; // No estamos en index.html
  }

  let todasLasPlagas = [];

  function renderizar(tipo) {
    grid.innerHTML = "";
    const listado = tipo === "todos"
      ? todasLasPlagas
      : todasLasPlagas.filter((p) => p.tipo === tipo);

    listado.forEach((plaga) => grid.appendChild(crearTarjetaPlaga(plaga)));
    estado.textContent = listado.length + " plaga(s) encontrada(s).";
  }

  botonesFiltro.forEach((boton) => {
    boton.addEventListener("click", () => {
      botonesFiltro.forEach((b) => {
        b.classList.remove("bg-vine-600", "border-vine-600", "text-white");
        b.classList.add("bg-white", "text-plum-900");
      });
      boton.classList.add("bg-vine-600", "border-vine-600", "text-white");
      boton.classList.remove("bg-white", "text-plum-900");
      renderizar(boton.dataset.tipo);
    });
  });

  fetch("/api/plagas")
    .then((respuesta) => {
      if (!respuesta.ok) {
        throw new Error("Respuesta no válida del servidor: " + respuesta.status);
      }
      return respuesta.json();
    })
    .then((datos) => {
      todasLasPlagas = datos;
      renderizar("todos");
    })
    .catch((error) => {
      estado.textContent = "No se pudo cargar el catálogo. Verifica que el backend esté activo.";
      console.error(error);
    });
}

function poblarSelect(select, items, textoOpcionInicial) {
  select.innerHTML = "";

  const opcionInicial = document.createElement("option");
  opcionInicial.value = "";
  opcionInicial.disabled = true;
  opcionInicial.selected = true;
  opcionInicial.textContent = textoOpcionInicial;
  select.appendChild(opcionInicial);

  items.forEach(({ value, label }) => {
    const opcion = document.createElement("option");
    opcion.value = value;
    opcion.textContent = label;
    select.appendChild(opcion);
  });
}

function inicializarSelectsDinamicos() {
  const selectLote = document.getElementById("lote");
  const selectPlaga = document.getElementById("plaga");

  if (!selectLote || !selectPlaga) {
    return; // No estamos en reportar.html
  }

  fetch("/api/lotes")
    .then((r) => r.json())
    .then((lotes) => {
      poblarSelect(
        selectLote,
        lotes.map((l) => ({ value: l.nombre, label: l.nombre })),
        "Selecciona un lote"
      );
    })
    .catch((error) => {
      poblarSelect(selectLote, [], "No se pudieron cargar los lotes");
      console.error(error);
    });

  fetch("/api/plagas")
    .then((r) => r.json())
    .then((plagas) => {
      poblarSelect(
        selectPlaga,
        plagas.map((p) => ({ value: p.nombreComun, label: p.nombreComun })),
        "Selecciona una plaga"
      );
    })
    .catch((error) => {
      poblarSelect(selectPlaga, [], "No se pudo cargar el catálogo");
      console.error(error);
    });
}

function inicializarFormularioReporte() {
  const formulario = document.getElementById("report-form");
  const feedback = document.getElementById("form-feedback");

  if (!formulario) {
    return; // No estamos en reportar.html
  }

  formulario.addEventListener("submit", (evento) => {
    evento.preventDefault();

    if (!formulario.checkValidity()) {
      formulario.reportValidity();
      return;
    }

    // FormData toma directamente los archivos de los <input type="file">,
    // por eso no se arma un objeto JSON manual: se envía tal cual como
    // multipart/form-data. La fecha NO se incluye; la asigna el backend.
    const datosFormulario = new FormData(formulario);

    feedback.textContent = "Enviando reporte…";
    feedback.className = "font-semibold text-plum-900/70";

    fetch("/api/reportes", {
      method: "POST",
      body: datosFormulario
    })
      .then((respuesta) => {
        if (!respuesta.ok) {
          throw new Error("El servidor respondió con estado " + respuesta.status);
        }
        return respuesta.json();
      })
      .then((creado) => {
        feedback.textContent = "Reporte #" + creado.id + " registrado correctamente.";
        feedback.className = "font-semibold text-vine-700";
        formulario.reset();
      })
      .catch((error) => {
        feedback.textContent = "No se pudo enviar el reporte. Intenta nuevamente.";
        feedback.className = "font-semibold text-severe-600";
        console.error(error);
      });
  });
}

document.addEventListener("DOMContentLoaded", () => {
  inicializarCatalogo();
  inicializarSelectsDinamicos();
  inicializarFormularioReporte();
});
