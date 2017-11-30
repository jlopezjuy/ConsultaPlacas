import { BaseEntity } from './../../shared';

export const enum Permiso {
    'AUTOS_ROBADOS',
    ' PADRON_VEHICULAR',
    ' AMBOS'
}

export class Radio implements BaseEntity {
    constructor(
        public id?: number,
        public descripcion?: string,
        public permiso?: Permiso,
        public marcaId?: number,
        public municipioId?: number,
        public corporacionId?: number,
        public tipoRadioId?: number,
    ) {
    }
}
