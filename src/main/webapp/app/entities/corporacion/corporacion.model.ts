import { BaseEntity } from './../../shared';

export class Corporacion implements BaseEntity {
    constructor(
        public id?: number,
        public descripcion?: string,
    ) {
    }
}
