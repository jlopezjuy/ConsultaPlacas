import { BaseEntity } from './../../shared';

export class Marca implements BaseEntity {
    constructor(
        public id?: number,
        public descripcion?: string,
    ) {
    }
}
