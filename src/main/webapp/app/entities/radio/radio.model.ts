import {BaseEntityRadio} from '../../shared/model/base-entity-radio';

export const enum Permiso {
    'AUTOS_ROBADOS',
    'PADRON_VEHICULAR',
    'AMBOS'
}

export class Radio implements BaseEntityRadio {
    constructor(
        public issi?: number,
        public descripcion?: string,
        public permiso?: Permiso,
        public marcaId?: number,
        public municipioId?: number,
        public corporacionId?: number,
        public tipoRadioId?: number,
    ) {
    }
}
