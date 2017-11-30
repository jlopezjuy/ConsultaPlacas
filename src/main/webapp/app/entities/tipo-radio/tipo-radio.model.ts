import { BaseEntity } from './../../shared';

export class TipoRadio implements BaseEntity {
    constructor(
        public id?: number,
        public descripcion?: string,
    ) {
    }
}
