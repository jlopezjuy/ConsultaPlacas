import { BaseEntity } from './../../shared';

export class Municipio implements BaseEntity {
    constructor(
        public id?: number,
        public descripcion?: string,
    ) {
    }
}
