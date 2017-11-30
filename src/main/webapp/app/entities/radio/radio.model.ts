import { BaseEntity } from './../../shared';

export class Radio implements BaseEntity {
    constructor(
        public id?: number,
        public descripcion?: string,
        public tipoDeRadio?: string,
        public marcaId?: number,
    ) {
    }
}
