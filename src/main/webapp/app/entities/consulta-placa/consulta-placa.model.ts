import { BaseEntity } from './../../shared';

export class ConsultaPlaca implements BaseEntity {
    constructor(
        public id?: number,
        public responsable?: string,
        public fecha?: any,
        public consulta?: string,
        public metodo?: string,
        public estado?: boolean,
        public resultado?: string,
        public coordenadas?: string,
        public radioId?: number,
    ) {
        this.estado = false;
    }
}
